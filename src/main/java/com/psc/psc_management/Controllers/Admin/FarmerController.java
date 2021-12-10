package com.psc.psc_management.Controllers.Admin;

import com.psc.psc_management.Models.Farmers;
import com.psc.psc_management.Services.Interfaces.BranchService;
import com.psc.psc_management.Services.Interfaces.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class FarmerController {
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private BranchService branchService;

    @GetMapping("/admin/farmers")
    public String showEmployeeTable(Model model) {
        List<Farmers> listFarmers = (List<Farmers>) farmerService.findAll();
        model.addAttribute("listFarmers", listFarmers);
        return "Views/Admin/Farmers/index";
    }

    // Add New Farmer to Farmers Table
    @GetMapping("/admin/farmers/create")
    public String showFarmerForm(Model model) {
        model.addAttribute("farmer", new Farmers());
        model.addAttribute("branch", branchService.findAll());
        return "Views/Admin/Farmers/create";
    }

    @PostMapping("/admin/farmers/save")
    public String saveFarmer(Farmers farmer, RedirectAttributes message) {
        farmerService.save(farmer);
        message.addFlashAttribute("success", "Success : The Farmer has been saved successfully");
        return "redirect:/admin/farmers";
    }

    @GetMapping("/admin/farmers/edit/{id}")
    public String editFarmer(@PathVariable("id") Integer id, Model model, RedirectAttributes message) {
        Optional<Farmers> farmer = farmerService.findById(id);
        if (farmer.isPresent()) {
            model.addAttribute("farmer", farmer);
            model.addAttribute("branches", branchService.findAll());
            return "/Views/Admin/Farmers/update";
        } else {
            message.addFlashAttribute("error", "Error: Farmer Doesn't Exist");
            return "redirect:/admin/farmers";
        }
    }

    @GetMapping("/admin/farmers/delete/{id}")
    public String deleteFarmer(@PathVariable("id") Integer id, RedirectAttributes message) {
        try {
            farmerService.deleteAllById(Collections.singleton(id));
            message.addFlashAttribute("success", "Success : Farmer Deleted Successfully");
            return "redirect:/admin/farmers";
        } catch (Exception e) {
            message.addFlashAttribute("error", "Error : Farmer Doesn't Exist");
            return "redirect:/admin/farmers";
        }
    }
}
