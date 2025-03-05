package com.travel.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.travel.web.config.Role;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Stop;
import com.travel.web.model.TripSearchCriteria;
import com.travel.web.model.User;
import com.travel.web.repository.UserRepository;
import com.travel.web.service.BookingService;
import com.travel.web.service.ScheduleTripService;
import com.travel.web.service.StopService;

@Controller
@RequestMapping("/public")
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingService bookingService;

    @Autowired
    private StopService stopService;

    @Autowired
    private ScheduleTripService scheduleTripService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/search")
    public String home(Model model) {
        List<Stop> allStops = stopService.getAllStops();
        model.addAttribute("allStops", allStops);
        List<ScheduleTrip> trips = scheduleTripService.searchTrips(LocalDate.now(), "Sonari", "Anala");
        trips.forEach(e -> System.out.println(e));
        return "trip-search";
    }

    @GetMapping("/login")
    public String loginPage() {

        return "login"; // Renders login.html from templates folder
    }

    @GetMapping("/signup")
    public String registerPage() {
        return "user-form"; // Renders the signup form
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user-form";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.setRole(Role.USER); // Set default role
        userRepository.save(user);
        return "redirect:/public/login"; // Redirect to login after registration
    }

    @PostMapping("/search")
    public String listTrips(Model model, @ModelAttribute TripSearchCriteria tripSearchCriteria, BindingResult result) {
        if (result.hasErrors()) {
            return "trip-search";
        }
        System.out.println(tripSearchCriteria); // For debugging, check the values passed

        // Now, you can use the values from tripSearchCriteria
        List<ScheduleTrip> trips = scheduleTripService.searchTrips(
                tripSearchCriteria.getTripDate(),
                tripSearchCriteria.getFromDestination(),
                tripSearchCriteria.getToDestination()
        );

        trips.forEach(e -> System.out.println(e.getRoute().getRouteName()));

        model.addAttribute("trips", trips);
        return "public-trip-list"; // Return the name of the view (Thymeleaf template)
    }
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }
    
    @GetMapping("/about-us")
    public String aboutUs() {
    	return "about-us";
    }
}
