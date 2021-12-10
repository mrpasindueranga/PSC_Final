package com.psc.psc_management.Controllers.Clerk;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Models.Farmers;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.FarmerService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Optional;

@Controller
public class ClerkDashboardController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FarmerService farmerService;

    @GetMapping("/clerk/farmers")
    public String farmerClerk(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Iterable<Farmers> listFarmers = farmerService.findAllByBranch(branch);
        model.addAttribute("listFarmers", listFarmers);

        return "/Views/Clerk/Farmers/index";
    }

    @GetMapping("/clerk/farmers/create")
    public String createFarmerClerk(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        model.addAttribute("farmer", new Farmers());
        model.addAttribute("branch", branch);

        return "/Views/Clerk/Farmers/create";
    }

    @PostMapping("/clerk/farmers/save")
    public String saveFarmerClerk(Farmers farmer, RedirectAttributes message) {
        farmerService.save(farmer);
        message.addFlashAttribute("success", "Success : The Farmer has been saved successfully");
        return "redirect:/clerk/farmers";
    }


    @GetMapping("/clerk/farmers/edit/{id}")
    public String editFarmer(@PathVariable("id") Integer id, Model model, RedirectAttributes message, @AuthenticationPrincipal MyUserDetails employee) {
        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Optional<Farmers> farmer = farmerService.findById(id);

        //Checking if Farmer Exists
        if (farmer.isPresent()) {
            //Checking if Clerk Is Trying to Edit A Farmer he has control over
            if (farmer.get().getBranch().getBranchName() == branch.getBranchName()){
                model.addAttribute("farmer",farmer);
                model.addAttribute("branches",branch);
                return "/Views/Clerk/Farmers/update";
            }else {
                message.addFlashAttribute("error", "Error: You Do Not Have Access Over This Farmer");
                return "redirect:/clerk/farmers";
            }
        } else {
            message.addFlashAttribute("error", "Error: Farmer Doesn't Exist");
            return "redirect:/clerk/farmers";
        }
    }

    @GetMapping("/clerk/farmers/delete/{id}")
    public String deleteFarmer(@PathVariable("id") Integer id, Model model, RedirectAttributes message, @AuthenticationPrincipal MyUserDetails employee){
        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Optional<Farmers> farmer = farmerService.findById(id);

        if (farmer.isPresent()) {
            if (farmer.get().getBranch().getBranchName() == branch.getBranchName()) {
                farmerService.deleteAllById(Collections.singleton(id));
                message.addFlashAttribute("success", "Success : Farmer Deleted Successfully");
            } else {
                message.addFlashAttribute("error", "Error: You Do Not Have Access Over This Farmer");
            }
        }
        else{
            message.addFlashAttribute("error", "Error: Farmer Doesn't Exist");
        }
        return "redirect:/clerk/farmers";
    }

}
