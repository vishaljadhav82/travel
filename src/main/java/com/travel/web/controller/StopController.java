package com.travel.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.web.model.Stop;
import com.travel.web.repository.RouteRepository;
import com.travel.web.repository.StopRepository;

@Controller
@RequestMapping("/admin/stops")
public class StopController {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping
    public String listStops(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "5") int size,
                            @RequestParam(required = false) Integer distance,
                            @RequestParam(required = false) Integer stopTime,
                            @RequestParam(required = false) String routeName,
                            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Stop> stopPage;

        if (distance != null || stopTime != null || (routeName != null && !routeName.isEmpty())) {
            stopPage = stopRepository.findByFilters(distance, stopTime, routeName, pageable);
        } else {
            stopPage = stopRepository.findAll(pageable);
        }

        model.addAttribute("stopPage", stopPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", stopPage.getTotalPages());

        // Pre-fill values back into form
        model.addAttribute("distance", distance);
        model.addAttribute("stopTime", stopTime);
        model.addAttribute("routeName", routeName);

        return "stop/stops-list";
    }
    
    

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("stop", new Stop());
        model.addAttribute("routes", routeRepository.findAll());
        return "stop/stop-add";
    }

    @PostMapping("/add")
    public String addStop(@ModelAttribute Stop stop) {
        stopRepository.save(stop);
        return "redirect:/admin/stops";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Stop stop = stopRepository.findById(id).orElseThrow();
        System.out.println("1");
        model.addAttribute("stop", stop);
        System.out.println("2");
        model.addAttribute("routes", routeRepository.findAll());
        System.out.println("4");
        return "stop/stop-add";
    }

    @PostMapping("/edit/{id}")
    public String updateStop(@PathVariable Long id, @ModelAttribute Stop stop) {
        stop.setId(id);
        stopRepository.save(stop);
        return "redirect:/admin/stops";
    }

    @GetMapping("/delete/{id}")
    public String deleteStop(@PathVariable Long id) {
        stopRepository.deleteById(id);
        return "redirect:/admin/stops";
    }
}
