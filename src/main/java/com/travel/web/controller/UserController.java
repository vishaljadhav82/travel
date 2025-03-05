package com.travel.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.web.model.Passenger;
import com.travel.web.model.User;
import com.travel.web.service.PassengerService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PassengerService passengerService;
    
    
    @GetMapping
    public String user(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName()); // Pass the username to Thymeleaf
        //System.out.println("______________________ "+auth.getName());
        return "welcomeUser";
    }

    @GetMapping("/passengers")
    public String listPassengers(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("passengers", passengerService.getAllPassengersByUsername(auth.getName()));
        return "passanger-list";
    }

    @GetMapping("/passengers/form")
    public String showPassengerForm(Model model) {
        model.addAttribute("passenger", new Passenger());
        return "passanger-form";
    }

    @PostMapping("/passengers/save")
    public String savePassenger(@ModelAttribute Passenger passenger) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      
        passengerService.savePassenger(passenger,auth.getName());
        return "redirect:/user/passengers/form";
    }
    
    @PostMapping("/passengers/save/edited")
    public String savePassengerEditited(@ModelAttribute Passenger passenger) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
      
        passengerService.updatePassenger(passenger.getId(), passenger, auth.getName());
        System.out.println("Saved");
        return "redirect:/user/passengers";
    }

    @GetMapping("/edit/{id}")
    public String editPassenger(@ModelAttribute User user, Model model) {
        model.addAttribute("passenger", passengerService.getPassengerById(user.getId()));
        return "passenger-form";
    }

    @GetMapping("/delete/{id}")
    public String deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return "redirect:/passengers";
    }
}
