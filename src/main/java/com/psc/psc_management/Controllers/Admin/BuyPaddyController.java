package com.psc.psc_management.Controllers.Admin;

import com.psc.psc_management.Models.BuyPaddy;
import com.psc.psc_management.Services.Interfaces.BuyPaddyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BuyPaddyController {
    @Autowired
    private BuyPaddyService buyPaddyService;

    @GetMapping("/admin/buyPaddy")
    public String buyPaddyIndex(Model model) {
        Iterable<BuyPaddy> buyPaddyList = buyPaddyService.findAll();
        model.addAttribute("buyPaddyList", buyPaddyList);
        return "Views/Admin/BuyPaddy/index";
    }
}
