package com.travel.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.web.model.Farmer;
import com.travel.web.repository.FarmerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/farmer")
public class FarmerController {

    @Autowired
    private FarmerRepository farmerRepository;

    // === Registration Form ===
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("farmer", new Farmer());
        return "farmer/registration"; // Create farmer/register.html
    }

    @PostMapping("/register")
    public String registerFarmer(@ModelAttribute Farmer farmer, Model model) {
        if (farmerRepository.findByEmail(farmer.getEmail()) != null) {
            model.addAttribute("error", "Farmer already exists with this email.");
            return "farmer/registration";
        }
        farmerRepository.save(farmer);
        model.addAttribute("message", "Registration successful. Please login.");
        return "redirect:/farmer/login";
    }

    // === Login Form ===
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("farmer", new Farmer()); // 🟢 Add this line
        return "farmer/login";
    }

    @PostMapping("/login")
    public String loginFarmer(@RequestParam String email,
                              @RequestParam String mobileNumber,
                              HttpSession session,
                              Model model) {
        Farmer farmer = farmerRepository.findByEmailAndMobileNumber(email, mobileNumber);
        if (farmer != null) {
            session.setAttribute("loggedInFarmer", farmer);
            return "redirect:/farmer/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or mobile number");
            return "farmer/login";
        }
    }

    // === Dashboard ===
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Farmer farmer = (Farmer) session.getAttribute("loggedInFarmer");
        if (farmer == null) {
            return "redirect:/farmer/login";
        }
        model.addAttribute("farmer", farmer);
        model.addAttribute("products", farmer.getProducts());
        return "farmer/dashboard"; // Create farmer/dashboard.html
    }

    // === Logout ===
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/farmer/login";
    }
}
