package com.psc.psc_management.Controllers.CollectionOfficer;

import java.util.Optional;

import com.psc.psc_management.Controllers.Admin.BranchController;
import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.BuyPaddy;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Models.Farmers;
import com.psc.psc_management.Models.Prices;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.BuyPaddyService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import com.psc.psc_management.Services.Interfaces.FarmerService;
import com.psc.psc_management.Services.Interfaces.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CollectionOfficerDashboardController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BuyPaddyService buyPaddyService;

    @Autowired
    private FarmerService farmerService;

    @Autowired
    private PriceService priceService;

    @Autowired
    private BranchController branchController;

    @GetMapping("/collectionOfficer/buyPaddy")
    public String collectionOfficerIndex(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Iterable<BuyPaddy> buyPaddyList = buyPaddyService.findAllByBranch(branch);
        model.addAttribute("buyPaddyList", buyPaddyList);

        return "/Views/Collection/BuyPaddy/index";
    }

    @GetMapping("/collectionOfficer/buyPaddy/create")
    public String createBuyPaddy(Model model, @AuthenticationPrincipal MyUserDetails employee) {
        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();
        Optional<Prices> prices = priceService.findById(1);

        Iterable<Farmers> listFarmers = farmerService.findAllByBranch(branch);

        model.addAttribute("listFarmers", listFarmers);
        model.addAttribute("branchList", branch);
        model.addAttribute("price", prices.get().getBuying());
        model.addAttribute("buyPaddy", new BuyPaddy());

        return "/Views/Collection/BuyPaddy/create";
    }

    @PostMapping("/collectionOfficer/buyPaddy/save")
    public String saveBuyPaddy(BuyPaddy buyPaddy, RedirectAttributes message,
            @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        // Check if the quantity is greater than the crop limit
        if (buyPaddy.getQuantity() > branch.getCropLimit()) {
            message.addFlashAttribute("error",
                    "Error : The Quantity is greater than the crop limit of the branch set for each farmer");
            return "redirect:/collectionOfficer/buyPaddy/create";
        } else {

            // Calculating cost of Purchase
            float cost = buyPaddy.getQuantity() * priceService.findById(1).get().getBuying();
            float leftBudget = branch.getBudget() - (branch.getUsedBudget() == null ? 0 : branch.getUsedBudget());

            // Checking If There is room in the Budget
            if (leftBudget > cost) {

                float oldUsedBudget = branch.getUsedBudget();
                float newUsedBudget = oldUsedBudget + cost;

                branch.setUsedBudget(newUsedBudget);
                buyPaddy.setCost(cost);

                float leftCapacity = branch.getCapacity() - branch.getUsedCapacity();

                // Checking If There is room in the collection center
                if (leftCapacity > buyPaddy.getQuantity()) {

                    float oldUsedCapacity = branch.getUsedCapacity();
                    float newUsedCapacity = oldUsedCapacity + buyPaddy.getQuantity();

                    branch.setUsedCapacity(newUsedCapacity);

                    branchController.saveBranch(branch);
                    buyPaddyService.save(buyPaddy);
                    message.addFlashAttribute("success", "Success : The Purchase has been added to the system");
                    return "redirect:/collectionOfficer/buyPaddy";

                } else {

                    message.addFlashAttribute("error", "Error : There is not enough space in the branch");
                    return "redirect:/collectionOfficer/buyPaddy/create";

                }
            } else {
                message.addFlashAttribute("error", "Error : This purchase is out of budget for your branch");
                return "redirect:/collectionOfficer/buyPaddy/create";
            }
        }
    }
}
