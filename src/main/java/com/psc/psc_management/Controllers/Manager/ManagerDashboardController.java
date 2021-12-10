package com.psc.psc_management.Controllers.Manager;

import com.lowagie.text.DocumentException;
import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.BuyPaddy;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Models.Prices;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.BranchService;
import com.psc.psc_management.Services.Interfaces.BuyPaddyService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import com.psc.psc_management.Services.Interfaces.PriceService;
import com.psc.psc_management.Services.PDFGeneratorEmployeeService;
import com.psc.psc_management.Services.PDFGeneratorManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ManagerDashboardController {

    @Autowired
    private PriceService priceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BuyPaddyService buyPaddyService;

    @GetMapping("/manager/dashboard")
    public String managerDashboard(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        // Get Paddy Buy and Sell Details
        Optional<Prices> prices = priceService.findById(1);
        Prices price = prices.get();
        Float PaddyBuyPrice = price.getBuying();
        Float PaddySellPrice = price.getSelling();

        model.addAttribute("paddyBuyPrice", PaddyBuyPrice);
        model.addAttribute("paddySellPrice", PaddySellPrice);

        // Get Used Budget

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Float branchBudget = branch.getBudget();
        Float usedBranchBudget = branch.getUsedBudget() == null ? 0 : branch.getUsedBudget();
        Float generatedIncome = branch.getIncome() == null ? 0 : branch.getIncome();

        float budgetLeft = branchBudget - usedBranchBudget + generatedIncome;
        model.addAttribute("budgetLeft", budgetLeft);

        // //Get Generated Income
        Float branchIncome = branch.getIncome();
        model.addAttribute("income", branchIncome);

        // //Branch Employee Count
        long EmployeeCount = employeeService.countByBranch(branch);
        model.addAttribute("EmployeeCount", EmployeeCount);

        // //Branch Used Capacity
        model.addAttribute("usedCapacity", branch.getUsedCapacity());

        // //Branch Left Capacity
        float branchLeftCapacity = branch.getCapacity() - branch.getUsedCapacity();
        model.addAttribute("leftCapacity", branchLeftCapacity);

        // //Branch Crop Limit
        model.addAttribute("farmerLimit", branch.getCropLimit());

        return "Views/Manager/Dashboard/index";
    }

    @GetMapping("/manager/report")
    public void exportToPDF(HttpServletResponse response, @AuthenticationPrincipal MyUserDetails employee)
            throws DocumentException, IOException {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=manager_report" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        Iterable<Employees> listEmployees = employeeService.findAllByBranch(branch);
        Iterable<BuyPaddy> listPaddyBuy = buyPaddyService.findAllByBranch(branch);

        PDFGeneratorManagerService exporter = new PDFGeneratorManagerService((List<Employees>) listEmployees,
                listPaddyBuy);
        exporter.export(response, branch.getId());
    }

}
