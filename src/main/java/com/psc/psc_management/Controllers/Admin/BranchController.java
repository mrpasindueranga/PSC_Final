package com.psc.psc_management.Controllers.Admin;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Services.Interfaces.BranchService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class BranchController {
    @Autowired
    private BranchService branchService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/admin/branches")
    public String indexBranch(Model model) {
        List<Branches> branches = (List<Branches>) branchService.findAll();
        model.addAttribute("branches", branches);
        return "Views/Admin/Branch/index";
    }

    @GetMapping("/admin/branches/create")
    public String createBranch(Model model) {
        model.addAttribute("branch", new Branches());
        return "Views/Admin/Branch/create";
    }

    @PostMapping("/admin/branches/save")
    public String saveBranch(Branches branch, RedirectAttributes message) {
        branchService.save(branch);
        message.addFlashAttribute("success", "Success : The Branch has been saved successfully");
        return "redirect:/admin/branches";
    }

    @GetMapping("/admin/branches/edit/{id}")
    public String editBranch(@PathVariable("id") Integer id, Model model, RedirectAttributes message) {
        Optional<Branches> branch = branchService.findById(id);
        if (branch.isPresent()) {
            model.addAttribute("branch", branch);
            return "/Views/Admin/Branch/update";
        } else {
            message.addFlashAttribute("error", "Error: Branch Doesn't Exist");
            return "redirect:/admin/branches";
        }
    }

    @GetMapping("/admin/branches/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, RedirectAttributes message) {
        try{
            branchService.deleteById(id);
            message.addFlashAttribute("success","Success : Branch Deleted Successfully");
            return "redirect:/branches";
        } catch (Exception e) {
            message.addFlashAttribute("error","Error : Branch Doesn't Exist");
        }
        return "redirect:/branches";
    }

    public void saveBranch(Branches branch) {
        branchService.save(branch);
    }
}
