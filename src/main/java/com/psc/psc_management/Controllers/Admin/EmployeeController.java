package com.psc.psc_management.Controllers.Admin;

import com.lowagie.text.DocumentException;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Services.Interfaces.BranchService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import com.psc.psc_management.Services.Interfaces.RoleService;
import com.psc.psc_management.Services.PDFGeneratorEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/admin/employees")
    public String showEmployeeTable(Model model) {
        List<Employees> listEmployees = (List<Employees>) employeeService.findAll();
        model.addAttribute("listEmployees", listEmployees);
        return "Views/Admin/Employees/index";
    }

    //Add New Employee to Employees Table
    @GetMapping("/admin/employees/create")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employees());
        model.addAttribute("branch", branchService.findAll());
        model.addAttribute("employeeRole",roleService.findAll());
        return "Views/Admin/Employees/create";
    }

    @PostMapping( "/admin/employees/save")
    public String saveEmployee(Employees employee, RedirectAttributes message) {
        employeeService.save(employee);
        message.addFlashAttribute("success", "Success : The Employee has been saved successfully");
        return "redirect:/admin/employees";
    }

    @GetMapping("/admin/employees/edit/{id}")
    public String editEmployee(@PathVariable("id") Integer id, Model model, RedirectAttributes message) {
        Optional<Employees> employee = employeeService.findById(id);
        if (employee.isPresent()){
            model.addAttribute("employee", employee);
            model.addAttribute("branches", branchService.findAll());
            model.addAttribute("employeeRole",roleService.findAll());
            return "/Views/Admin/Employees/update";
        }else {
            message.addFlashAttribute("error","Error: Employee Doesn't Exist");
            return "redirect:/admin/employees";
        }
    }

    @GetMapping("/admin/employees/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id, RedirectAttributes message) {
        try{
            employeeService.deleteAllById(Collections.singleton(id));
            message.addFlashAttribute("success","Success : Employee Deleted Successfully");
            return "redirect:/employees";
        } catch (Exception e) {
            message.addFlashAttribute("error","Error : Employee Doesn't Exist");
            return "redirect:/admin/employees";
        }
    }

    @GetMapping("/admin/employees/report")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employee_report" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Employees> listEmployees = (List<Employees>) employeeService.findAll();

        PDFGeneratorEmployeeService exporter = new PDFGeneratorEmployeeService(listEmployees);
        exporter.export(response);
    }
}
