package com.travel.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.web.model.Bus;
import com.travel.web.model.Route;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.repository.BusRepository;
import com.travel.web.repository.RouteRepository;
import com.travel.web.service.ScheduleTripService;

@Controller
@RequestMapping("/admin/scheduleTrip")
public class ScheduleTripController {

    @Autowired
    private ScheduleTripService scheduleTripService;
    
    @Autowired
    BusRepository busRepository;
    
    @Autowired
    RouteRepository routeRepository;

    // List all schedule trips
    @GetMapping("/list")
    public String listTrips(Model model) {
        List<ScheduleTrip> trips = scheduleTripService.getAllTrips();
        model.addAttribute("trips", trips);
        return "trip/trip-list"; // Thymeleaf template (scheduleTrips/list.html)
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Bus> buses = busRepository.findAll().stream().filter(e -> e.isAvailable()).toList();
        List<Route> routes = routeRepository.findAll();
        model.addAttribute("trip", new ScheduleTrip());
        model.addAttribute("buses", buses);
        model.addAttribute("routes", routes);

        return "trip/trip-form"; // Thymeleaf form for adding/updating
    }

    // Save new trip
    @PostMapping("/save")
    public String saveTrip(@ModelAttribute ScheduleTrip trip) {
        scheduleTripService.saveTrip(trip);
        return "redirect:/admin/scheduleTrip/list";
    }

    // Show edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        ScheduleTrip trip = scheduleTripService.getTripById(id);
      
        if (trip != null) {
            model.addAttribute("trip", trip);
            return "trip/trip-form-edit";
        }
        return "redirect:/admin/scheduleTrip/list";
    }

    // Update trip
    @PostMapping("/update/{id}")
    public String updateTrip(@PathVariable Long id, @ModelAttribute ScheduleTrip trip) {
    	
        scheduleTripService.updateTrip(id, trip);
        return "redirect:/admin/scheduleTrip/list";
    }

    // Delete trip
    @GetMapping("/delete/{id}")
    public String deleteTrip(@PathVariable Long id) {
        scheduleTripService.deleteTrip(id);
        return "redirect:/admin/scheduleTrip/list";
    }
    
 // Route details API
    @GetMapping("/route/details/{id}")
    @ResponseBody
    public Route getRouteDetails(@PathVariable Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    // Bus details API
    @GetMapping("/bus/details/{id}")
    @ResponseBody
    public Bus getBusDetails(@PathVariable Long id) {
        return busRepository.findById(id).orElse(null);
    }

}
