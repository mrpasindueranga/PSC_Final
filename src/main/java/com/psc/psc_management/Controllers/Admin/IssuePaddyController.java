package com.psc.psc_management.Controllers.Admin;

import com.psc.psc_management.Models.IssuePaddy;
import com.psc.psc_management.Services.Interfaces.BranchService;
import com.psc.psc_management.Services.Interfaces.IssuepaddyService;
import com.psc.psc_management.Services.Interfaces.PriceService;
import com.psc.psc_management.Services.Interfaces.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class IssuePaddyController {
    @Autowired
    private IssuepaddyService issuePaddyService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private BranchController branchController;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private PriceService priceService;

    @GetMapping("/admin/issuePaddy")
    public String showIssuePaddyTable(Model model) {
        List<IssuePaddy> listIssuePaddy = (List<IssuePaddy>) issuePaddyService.findAll();
        model.addAttribute("listIssuePaddy", listIssuePaddy);
        return "Views/Admin/IssuePaddy/index";
    }

    @GetMapping("/admin/issuePaddy/create")
    public String showIssuePaddyForm(Model model) {
        model.addAttribute("issuePaddy", new IssuePaddy());
        model.addAttribute("branch", branchService.findAll());
        model.addAttribute("vehicle", vehicleService.findAll());
        return "Views/Admin/IssuePaddy/create";
    }

    @PostMapping("/admin/issuePaddy/save")
    public String saveIssuePaddy(IssuePaddy issuePaddy, RedirectAttributes message) {
        long issuePaddyDate = issuePaddyService.countByVehicleAndDate(issuePaddy.getVehicle(),issuePaddy.getDate());

        Float used_capacity_branch;
        if (issuePaddy.getBranch().getUsedCapacity()==null){
            used_capacity_branch = Float.valueOf(0);
        }else {
            used_capacity_branch = issuePaddy.getBranch().getUsedCapacity();
        }

        //Check if the branch has enough supply to support the sale
        if (used_capacity_branch>issuePaddy.getQuantity()){

            //Check if that particular vehicle is free on that date
            if (issuePaddyDate==1){
                message.addFlashAttribute("error", "Error : The selected vehicle is not free on that date");
                return "redirect:/admin/issuePaddy/create";
            }else {

                //Check if that particular vehicle can hold that specific quantity
                if (issuePaddy.getQuantity()>issuePaddy.getVehicle().getCapacity()){
                    message.addFlashAttribute("error", "Error : The selected vehicle, cannot hold the specific quantity");
                    return "redirect:/admin/issuePaddy/create";
                }else {
                    //Generate Revenue Of Sale
                    float revenue = generateRevenue(issuePaddy);
                    issuePaddy.setRevenue(revenue);

                    issuePaddyService.save(issuePaddy);
                    message.addFlashAttribute("success", "Success : The Issue Paddy Request has been saved successfully");
                    return "redirect:/admin/issuePaddy";
                }
            }
        }
        else {
            message.addFlashAttribute("error", "Error : The Branch Does Not Have Enough Stocks");
            return "redirect:/admin/issuePaddy/create";
        }
    }
    
    @GetMapping("/admin/issuePaddy/delete/{id}")
    public String deleteIssuePaddy(@PathVariable("id") Integer id, RedirectAttributes message) {
        Optional<IssuePaddy> issuePaddy = issuePaddyService.findById(id);
        IssuePaddy issuePaddyInfo = issuePaddy.get();
        String issuePaddyStatus = issuePaddyInfo.getStatus();
        if (Objects.equals(issuePaddyStatus, "Request")){
            try {
                issuePaddyService.deleteById(id);
                message.addFlashAttribute("success", "Success : IssuePaddy Request Deleted Successfully");

                float branchIncome = issuePaddyInfo.getBranch().getIncome();
                float issuePaddyRevenue = issuePaddyInfo.getRevenue();
                float branchCurrentIncome = branchIncome - issuePaddyRevenue;

                issuePaddyInfo.getBranch().setIncome(branchCurrentIncome);
                branchController.saveBranch(issuePaddyInfo.getBranch());

                return "redirect:/admin/issuePaddy";
            } catch (Exception e) {
                message.addFlashAttribute("error", "Error : IssuePaddy Request Doesn't Exist");
                return "redirect:/admin/issuePaddy";
            }
        }else {
            message.addFlashAttribute("error", "Error Deleting : IssuePaddy Request Has Already Been Delivered");
            return "redirect:/admin/issuePaddy";
        }

    }

    public float generateRevenue(IssuePaddy issuePaddy){
        float quantity = issuePaddy.getQuantity();
        float sellPrice = priceService.findById(1).get().getSelling();
        float revenue = quantity*sellPrice;

        Float branchCurrentIncome = issuePaddy.getBranch().getIncome();
        float income;
        if (branchCurrentIncome == null){
            income = 0 + revenue;
        }else {
            income = branchCurrentIncome + revenue;
        }
        issuePaddy.getBranch().setIncome(income);
        branchController.saveBranch(issuePaddy.getBranch());

        return revenue;
    }

}
