
package com.travel.web.controller;

import com.travel.web.model.Bus;
import com.travel.web.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequestMapping("/admin/buses")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @GetMapping
    public String listBuses(Model model) {
        List<Bus> buses = busRepository.findAll();
        model.addAttribute("buses", buses);
        return "bus/bus-list";
    }

    @GetMapping("/new")
    public String createBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "bus/bus-form";
    }

    @PostMapping
    public String saveBus(@ModelAttribute Bus bus) {
        busRepository.save(bus);
        return "redirect:/admin/buses";
    }
}