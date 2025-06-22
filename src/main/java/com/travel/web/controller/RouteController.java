// RouteController.java
package com.travel.web.controller;

import com.travel.web.model.Bus;
import com.travel.web.model.Route;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Stop;
import com.travel.web.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/list")
    public String listRoutes(Model model) {
        model.addAttribute("routes", routeService.getAllRoutes());
        return "routes/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("route", new Route());
        return "routes/create-route";
    }

    @PostMapping("/create")
    public String createRoute(@ModelAttribute Route route) {
        routeService.createRoute(route);
        return "redirect:/admin/routes/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Route route = routeService.getRouteById(id).orElseThrow();
        model.addAttribute("route", route);
        return "routes/form";
    }

    @PostMapping("/edit/{id}")
    public String updateRoute(@PathVariable Long id, @ModelAttribute Route route) {
        routeService.updateRoute(id, route);
        return "redirect:/admin/routes/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return "redirect:/admin/routes/list";
    }

    @GetMapping("/{routeId}/stops")
    public String viewStops(@PathVariable Long routeId, Model model) {
        Route route = routeService.getRouteById(routeId).orElseThrow();
        List<Stop> stops = routeService.getStopsForRoute(routeId);
        model.addAttribute("route", route);
        model.addAttribute("stops", stops);
        model.addAttribute("stop", new Stop());
        return "stop/stops";
    }

    @PostMapping("/{routeId}/stops")
    public String addStop(@PathVariable Long routeId, @ModelAttribute Stop stop) {
    	System.out.println("Inside stop add");
        routeService.addStopToRoute(routeId, stop);
        return "redirect:/admin/routes/" + routeId + "/stops";
    }

    @GetMapping("/{routeId}/stops/delete/{stopId}")
    public String deleteStop(@PathVariable Long routeId, @PathVariable Long stopId) {
        routeService.deleteStop(stopId);
        return "redirect:/admin/routes/" + routeId + "/stops";
    }
    
}
