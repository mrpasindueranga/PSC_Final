package com.psc.psc_management.Controllers.Manager;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import com.psc.psc_management.Services.Interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Controller
public class ManagerEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/manager/employees")
    public String showEmployeeTable(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Iterable<Employees> employeeList = employeeService.findAllByBranch(branch);
        model.addAttribute("listEmployees", employeeList);

        return "Views/Manager/Employees/index";
    }

    @GetMapping("/manager/employees/create")
    public String showEmployeeForm(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        model.addAttribute("employee", new Employees());
        model.addAttribute("branch", branch);
        model.addAttribute("employeeRole",roleService.findAll());

        return "Views/Manager/Employees/create";
    }

    @PostMapping( "/manager/employees/save")
    public String saveEmployee(Employees employee, RedirectAttributes message) {
        employeeService.save(employee);
        message.addFlashAttribute("success", "Success : The Employee has been saved successfully");
        return "redirect:/manager/employees";
    }

    @GetMapping("/manager/employees/edit/{id}")
    public String editEmployee(@PathVariable("id") Integer id, Model model, RedirectAttributes message, @AuthenticationPrincipal MyUserDetails employee) {
        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Optional<Employees> employees1 = employeeService.findById(id);

        if (employees1.isPresent()){
            if (Objects.equals(employees1.get().getBranch().getBranchName(), branch.getBranchName())){
                model.addAttribute("employee", employees1);
                model.addAttribute("branches", branch);
                model.addAttribute("employeeRole",roleService.findAll());
                return "/Views/Manager/Employees/update";
            }else {
                message.addFlashAttribute("error","Error: You Do Not Have Authority Over This Employee");
                return "redirect:/manager/employees";
            }
        }else {
            message.addFlashAttribute("error","Error: Employee Doesn't Exist");
            return "redirect:/manager/employees";
        }
    }

    @GetMapping("/manager/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, RedirectAttributes message, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        if (employeeService.findById(id).isPresent()){
            if (employeeService.findById(id).get().getBranch().getBranchName() == branch.getBranchName()){
                employeeService.deleteAllById(Collections.singleton(id));
                message.addFlashAttribute("success","Success : Employee Deleted Successfully");
            }else {
                message.addFlashAttribute("error","Error : You Do Not Have Authority Over This Employee");
            }
        }else {
            message.addFlashAttribute("error","Error : Employee Doesn't Exist");
        }
        return "redirect:/manager/employees";
    }

}
