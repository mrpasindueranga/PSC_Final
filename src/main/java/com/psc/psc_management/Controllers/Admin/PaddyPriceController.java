package com.psc.psc_management.Controllers.Admin;

import com.psc.psc_management.Models.Prices;
import com.psc.psc_management.Services.Interfaces.PriceService;
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
public class PaddyPriceController {
    @Autowired
    private PriceService priceService;

    @GetMapping("/admin/prices")
    public String indexPrice(Model model) {
        List<Prices> listPrices = (List<Prices>) priceService.findAll();
        model.addAttribute("listPrices", listPrices);
        return "Views/Admin/PaddyPrice/index";
    }

    @GetMapping("/admin/prices/create")
    public String formPrice(Model model, RedirectAttributes message) {
        if (priceService.count()==0){
            model.addAttribute("price", new Prices());
            return "Views/Admin/PaddyPrice/create";
        }else {
            message.addFlashAttribute("error", "Error : The price is already set, Please update existing record.");
            return "redirect:/admin/prices";
        }
    }

    @PostMapping("/admin/prices/save")
    public String savePrice(Prices price, RedirectAttributes message) {
        priceService.save(price);
        message.addFlashAttribute("success", "Success : The price has been saved successfully");
        return "redirect:/admin/prices";
    }

    @GetMapping("/admin/prices/edit/{id}")
    public String editPrice(@PathVariable("id") Integer id, Model model, RedirectAttributes message) {
        Optional<Prices> price = priceService.findById(id);
        if (price.isPresent()) {
            model.addAttribute("price", price);
            return "/Views/Admin/PaddyPrice/update";
        } else {
            message.addFlashAttribute("error", "Error: Price Doesn't Exist");
            return "redirect:/admin/prices";
        }
    }

}
