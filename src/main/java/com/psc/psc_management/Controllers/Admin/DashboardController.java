package com.psc.psc_management.Controllers.Admin;

import com.psc.psc_management.Services.Authentication.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/admin")
    public String index() {
        return "Views/Admin/Dashboard/index";
    }

}
