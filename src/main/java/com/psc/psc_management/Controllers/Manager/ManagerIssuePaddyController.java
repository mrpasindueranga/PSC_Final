package com.psc.psc_management.Controllers.Manager;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Models.IssuePaddy;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import com.psc.psc_management.Services.Interfaces.IssuepaddyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerIssuePaddyController {
    
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private IssuepaddyService issuePaddyService;

    @GetMapping("/manager/issuePaddy")
    public String issuePaddyIndex(Model model,@AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Iterable<IssuePaddy> listIssuePaddy = issuePaddyService.findAllByBranch(branch);
        model.addAttribute("listIssuePaddy", listIssuePaddy);

        return "Views/Manager/IssuePaddy/index";
    }

}
