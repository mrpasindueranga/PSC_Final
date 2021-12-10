package com.psc.psc_management.Controllers.Admin;

import java.util.List;
import java.util.Optional;

import com.psc.psc_management.Models.Vehicles;
import com.psc.psc_management.Services.Interfaces.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/admin/vehicles")
    public String indexVehicle(Model model) {
        List<Vehicles> listVehicles = (List<Vehicles>) vehicleService.findAll();
        model.addAttribute("listVehicles", listVehicles);
        return "Views/Admin/Vehicles/index";
    }

    @GetMapping("/admin/vehicles/create")
    public String createVehicle(Model model) {
        model.addAttribute("vehicle", new Vehicles());
        return "Views/Admin/Vehicles/create";
    }

    @PostMapping("/admin/vehicles/save")
    public String saveVehicle(Vehicles vehicle, RedirectAttributes message) {
        vehicleService.save(vehicle);
        message.addFlashAttribute("success", "Success : The Vehicle has been saved successfully");
        return "redirect:/admin/vehicles";
    }

    @GetMapping("/admin/vehicles/edit/{id}")
    public String editVehicle(@PathVariable("id") Integer id, Model model, RedirectAttributes message) {
        Optional<Vehicles> vehicle = vehicleService.findById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle);
            return "/Views/Admin/Vehicles/update";
        } else {
            message.addFlashAttribute("error", "Error: Vehicle Doesn't Exist");
            return "redirect:/admin/vehicles";
        }
    }

    @GetMapping("/admin/vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable("id") Integer id, RedirectAttributes message) {
        try {
            vehicleService.deleteById(id);
            message.addFlashAttribute("success", "Success : Vehicle Deleted Successfully");
            return "redirect:/admin/vehicles";
        } catch (Exception e) {
            message.addFlashAttribute("error", "Error : Vehicle Doesn't Exist");
        }
        return "redirect:/admin/vehicles";
    }
}
