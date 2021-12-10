package com.psc.psc_management.Controllers.Manager;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Models.Farmers;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import com.psc.psc_management.Services.Interfaces.FarmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerFarmerController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private FarmerService farmerService;

    @GetMapping("/manager/farmers")
    public String buyPaddyIndex(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Iterable<Farmers> listFarmers = farmerService.findAllByBranch(branch);
        model.addAttribute("listFarmers", listFarmers);

        return "Views/Manager/Farmers/index";
    }
}
