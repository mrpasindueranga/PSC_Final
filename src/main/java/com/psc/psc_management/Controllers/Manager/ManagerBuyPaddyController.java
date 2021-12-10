package com.psc.psc_management.Controllers.Manager;

import com.psc.psc_management.Models.Branches;
import com.psc.psc_management.Models.BuyPaddy;
import com.psc.psc_management.Models.Employees;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.BuyPaddyService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerBuyPaddyController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BuyPaddyService buyPaddyService;

    @GetMapping("/manager/buyPaddy")
    public String buyPaddyIndex(Model model, @AuthenticationPrincipal MyUserDetails employee) {

        String loggedUserName = employee.getUsername();
        Employees employees = employeeService.getEmployeesByEmployeeName(loggedUserName);
        Branches branch = employees.getBranch();

        Iterable<BuyPaddy> buyPaddyList = buyPaddyService.findAllByBranch(branch);
        model.addAttribute("buyPaddyList", buyPaddyList);

        return "Views/Manager/BuyPaddy/index";
    }
}
