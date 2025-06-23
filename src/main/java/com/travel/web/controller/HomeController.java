package com.travel.web.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.travel.web.config.Role;
import com.travel.web.model.Booking;
import com.travel.web.model.PaymentResponse;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Stop;
import com.travel.web.model.TripSearchCriteria;
import com.travel.web.model.User;
import com.travel.web.repository.UserRepository;
import com.travel.web.service.BookingService;
import com.travel.web.service.EmailService;
import com.travel.web.service.ScheduleTripService;
import com.travel.web.service.StopService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/public")
public class HomeController {

	@Autowired
	private EmailService emailService;

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
	public String home(Model model) {
		List<Stop> allStops = stopService.getAllStops();
		model.addAttribute("allStops", allStops);
		List<ScheduleTrip> trips = scheduleTripService.searchTrips(LocalDate.now(), "Sonari", "Anala");
		trips.forEach(e -> System.out.println(e));
		return "public/t-search";
	}

//    @GetMapping("/send-otp")
//    @ResponseBody
//    public String sendOtp(@RequestParam String email) {
//        String otp = emailService.sendOTP(email);
//        return "OTP sent to " + email;
//    }

	@GetMapping("/login")
	public String loginPage() {

		return "public/login"; // Renders login.html from templates folder
	}

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		model.addAttribute("user", new User());
		return "public/user-form";
	}

	@PostMapping("/signup")
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
		if (result.hasErrors()) {
			return "public/user-form";
		}

		if (userRepository.existsByUsername(user.getUsername())) {
			result.rejectValue("username", "error.user", "Username is already registered.");
			return "public/user-form";
		}

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(Role.USER);
		userRepository.save(user);
		return "redirect:/public/login";
	}

	@PostMapping("/search")
	public String listTrips(Model model, @ModelAttribute TripSearchCriteria tripSearchCriteria, BindingResult result) {
		if (result.hasErrors()) {
			return "public/t-search";
		}
		System.out.println(tripSearchCriteria); // For debugging, check the values passed

		// Now, you can use the values from tripSearchCriteria
		List<ScheduleTrip> trips = scheduleTripService.searchTrips(tripSearchCriteria.getTripDate(),
				tripSearchCriteria.getFromDestination(), tripSearchCriteria.getToDestination());

		trips.forEach(e -> System.out.println(e.getRoute().getRouteName()));

		model.addAttribute("trips", trips);
		return "public/public-trip-list"; // Return the name of the view (Thymeleaf template)
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("message", ex.getMessage());
		return modelAndView;
	}

	@GetMapping("/about-us")
	public String aboutUs() {
		return "public/about-us";
	}

	@GetMapping("/testing")
	@ResponseBody
	public List<Booking> test() {
		List<Booking> bookings = bookingService.findAllByUsername("admin");
		return bookings;
	}
	
	//password resetting methods
    // Step 1: Show forget password form
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "reset/forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendOtp(@RequestParam("username") String username, HttpSession session, Model model) {
        // First check if username exists
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            model.addAttribute("error", "Username not found.");
            return "reset/forgot-password";
        }

        User user = optionalUser.get();

        // Now send OTP to user's email
        String otp = emailService.sendOtpEmail(user.getEmail());

        if (otp != null) {
            session.setAttribute("otp", otp);
            session.setAttribute("otpEmail", user.getEmail());
            session.setAttribute("otpUsername", user.getUsername());  // also store username if needed
            return "redirect:/public/verify-otp";
        } else {
            model.addAttribute("error", "Failed to send OTP. Please try again.");
            return "reset/forgot-password";
        }
    }

    // Step 3: Show OTP form
    @GetMapping("/verify-otp")
    public String showOtpForm() {
        return "reset/verify-otp";
    }

 // Step 4: Verify OTP
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String inputOtp, HttpSession session, Model model) {
        String sessionOtp = (String) session.getAttribute("otp");
        String sessionEmail = (String) session.getAttribute("otpEmail");
        String sessionUsername = (String) session.getAttribute("otpUsername");

        if (sessionOtp == null || sessionEmail == null) {
            model.addAttribute("error", "Session expired. Please try again.");
            return "redirect:/public/forgot-password";
        }

        if (sessionOtp.equals(inputOtp)) {
            // OTP success → Allow reset password
            session.setAttribute("otpVerified", true);

            // Optional: Clear OTP from session (no need to store it after success)
            session.removeAttribute("otp");

            return "redirect:/public/reset-password";
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "reset/verify-otp";
        }
    }
 // Step 5: Show Reset Password form
    @GetMapping("/reset-password")
    public String showResetPasswordForm(HttpSession session) {
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");

        if (otpVerified != null && otpVerified) {
            return "reset/reset-password";
        } else {
            return "redirect:/public/forgot-password";
        }
    }

    // Step 6: Save new password
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("password") String password, HttpSession session, Model model) {
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");
        String email = (String) session.getAttribute("otpEmail");

        if (otpVerified == null || !otpVerified || email == null) {
            model.addAttribute("error", "Session expired or invalid access. Please start again.");
            return "redirect:/public/forgot-password";
        }

        User user = userRepository.findByEmail(email);

        if (user != null) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);

            // Clear session attributes
            session.removeAttribute("otp");
            session.removeAttribute("otpEmail");
            session.removeAttribute("otpUsername");
            session.removeAttribute("otpVerified");

            model.addAttribute("message", "Password reset successfully. You can now log in.");
            return "redirect:/public/login";
        } else {
            model.addAttribute("error", "Something went wrong. Please try again.");
            return "reset/reset-password";
        }
    }
    
    @PostMapping("/handle-payment-callback")
    @ResponseBody
    public Map<String, String> confirmPayment(@RequestBody PaymentResponse paymentResponse) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Booking booking = bookingService.confimBooking(paymentResponse.getId(), paymentResponse.getOrderId(), auth.getName(), paymentResponse.getPaymentId());
        ScheduleTrip scheduleTrip = scheduleTripService.getTripById(booking.getTripId());

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("redirectUrl", "/public/search");

        return response;
    }

}
