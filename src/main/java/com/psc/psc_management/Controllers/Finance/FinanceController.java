package com.psc.psc_management.Controllers.Finance;

import com.psc.psc_management.Models.BuyPaddy;
import com.psc.psc_management.Services.Authentication.MyUserDetails;
import com.psc.psc_management.Services.Interfaces.BuyPaddyService;
import com.psc.psc_management.Services.Interfaces.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinanceController {

  @Autowired
  private BuyPaddyService buyPaddyService;

  @GetMapping("/finance")
  public String index(Model model, @AuthenticationPrincipal MyUserDetails employee) {
    Iterable<BuyPaddy> buyPaddies = buyPaddyService.findAll();
    model.addAttribute("buyPaddies", buyPaddies);
    return "/Views/Finance/index";
  }

  @GetMapping("/finance/paid")
  public String paid(Model model, @AuthenticationPrincipal MyUserDetails employee) {
    Iterable<BuyPaddy> buyPaddies = buyPaddyService.findAll();
    model.addAttribute("buyPaddies", buyPaddies);
    return "/Views/Finance/paid";
  }
}
