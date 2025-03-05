package com.travel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.travel.web.custom.exception.ResourceNotFoundException;

@Controller
@RequestMapping("/test")
public class ExceptionTester {

    @GetMapping("/user")
    public String getUser(@RequestParam(required = false) String id, Model model) {
        if (id == null) {
            throw new IllegalArgumentException("User ID is required");
        }
        if ("123".equals(id)) {
            model.addAttribute("user", "User Found!");
            return "user-profile";
        }
        throw new ResourceNotFoundException("User with ID " + id + " not found");
    }

    @GetMapping("/error")
    public String generateError() {
        throw new RuntimeException("Something went wrong!");
    }
}
