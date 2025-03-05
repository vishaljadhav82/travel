package com.travel.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.travel.web.model.Bus;
import com.travel.web.model.Seat;
import com.travel.web.repository.BusRepository;
import com.travel.web.repository.SeatRepository;
import com.travel.web.service.RouteService;
import com.travel.web.service.StopService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BusRepository busRepository;
    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    public AdminController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @GetMapping
    public String admin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "welcomeAdmin";
    }

    @GetMapping("/add")
    public String showAddBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "add-bus";
    }

    @PostMapping("/add")
    public String addBus(@ModelAttribute Bus bus, RedirectAttributes redirectAttributes) {
        try {
            bus.setAvailable(true);
            for (int i = 1; i <= bus.getCapacity(); i++) {
                Seat seat = new Seat();
                seat.setSeatNumber(i);
                Seat saved = seatRepository.save(seat);
                bus.getBusSeats().add(saved);
            }
            busRepository.save(bus);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error adding bus: " + e.getMessage());
            return "redirect:/admin/add";
        }
        return "redirect:/admin/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteBus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (busRepository.existsById(id)) {
                busRepository.deleteById(id);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting bus: " + e.getMessage());
        }
        return "redirect:/admin/list";
    }

    @PostMapping("/update/{id}")
    public String updateBus(@PathVariable Long id, @ModelAttribute Bus updatedBus, RedirectAttributes redirectAttributes) {
        try {
            Optional<Bus> existingBus = busRepository.findById(id);
            if (existingBus.isPresent()) {
                Bus bus = existingBus.get();
                bus.setBusNumber(updatedBus.getBusNumber());
                bus.setDriverName(updatedBus.getDriverName());
                bus.setCapacity(updatedBus.getCapacity());
                busRepository.save(bus);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating bus: " + e.getMessage());
        }
        return "redirect:/admin/list";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "redirect:/admin";
    }
}
