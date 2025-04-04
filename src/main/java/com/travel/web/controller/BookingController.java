package com.travel.web.controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travel.web.model.Booking;
import com.travel.web.model.BookingCriteria;
import com.travel.web.model.Passenger;
import com.travel.web.model.PaymentResponse;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Stop;
import com.travel.web.repository.UserRepository;
import com.travel.web.service.BookingService;
import com.travel.web.service.PassengerService;
import com.travel.web.service.RouteService;
import com.travel.web.service.ScheduleTripService;
import com.travel.web.service.StopService;

@Controller
@RequestMapping("/user/booking")
public class BookingController {

    @Autowired
    private StopService stopService;  // Service to fetch stops
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RouteService routeService;
    @Autowired
   private ScheduleTripService scheduleTripService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private BookingService bookingService;

    
    @GetMapping("/book/{tripId}")
    public String showBookingForm(@PathVariable("tripId") Long tripId,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<Passenger> passenger =   userRepository.findByUsername(auth.getName()).get().getPassengers();
        List<Stop> stops = scheduleTripService.getTripById(tripId).getRoute().getStops().stream().sorted(Comparator.comparing(Stop::getDistanceFromStart)).toList();  // Get all stops
      //  stops.forEach(e -> e.getRoute().getStops().forEach(s -> System.out.println(s.getStopName())));
        model.addAttribute("stops", stops);
        model.addAttribute("passenger", passenger);
        model.addAttribute("tripId", tripId);
                return "book/book-form";  // The name of your Thymeleaf HTML file
    }

    @PostMapping(value="/save",produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> saveBooking(@RequestBody BookingCriteria bookingCriteria) throws Exception {
        // Save the booking to the database
        // Add your booking saving logic here
    	System.out.println(bookingCriteria.toString());
    	double cost = 0;
    	double preDistance = 0;
    	double distance = 0;
    	double startDistance = 0;
    	double currentDistance = 0;
    	double time = 0;
    	int index = -1;
    	int dindex = -1;
    	System.out.println("1");
    ScheduleTrip myTrip =	scheduleTripService.getTripById(bookingCriteria.getTripId());
    	double costPK = myTrip.getCostPerKm();
    	System.out.println("Here 2");
    	
    	
      System.out.println(bookingCriteria.toString());
    List<Stop> stops =  scheduleTripService.getTripById(bookingCriteria.getTripId()).getRoute().getStops().stream().sorted(Comparator.comparing(Stop::getDistanceFromStart)).toList();
            for (int i = 0; i < stops.size(); i++) {
				if (stops.get(i).getStopName().equals(bookingCriteria.getFromLocation())) {
					index = i;
				}
				if (stops.get(i).getStopName().equals(bookingCriteria.getDestinationLocation())) {
					dindex = i;
					break;
				}
			}

            distance = stops.get(dindex).getDistanceFromStart() - stops.get(index).getDistanceFromStart() ;
            
            System.out.println("Total Distance: "+distance);
            cost = distance * 1.2;
     
     Booking booking = new Booking();
     booking.setDestinationLocation(bookingCriteria.getDestinationLocation());
     booking.setFromLocation(bookingCriteria.getFromLocation());
     booking.setTripId(bookingCriteria.getTripId());
     booking.setSeatNumber(1);
     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Passenger passenger = passengerService.getPassengerById(bookingCriteria.getPassengerId()).get();
     
     booking.setPassenger(passenger);
     booking.setRupeesPaid(cost);
     booking.setTotalDistance(distance);
    int seat = bookingService.checkAvailability(bookingCriteria.getFromLocation(), bookingCriteria.getDestinationLocation(), bookingCriteria.getTripId());
    booking.setSeatNumber(seat);
    Booking booked = bookingService.creatBooking(booking,auth.getName());
	return new ResponseEntity<>(booking,HttpStatus.CREATED);
     
         }
     
    @PostMapping("/handle-payment-callback")
    public String confirmPayment(@RequestBody PaymentResponse paymentResponse, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       System.out.println(paymentResponse.toString());
        // Extract payment details from the response
        long id = paymentResponse.getId();
        String orderId = paymentResponse.getOrderId();
        String paymentId = paymentResponse.getPaymentId();
        String signatureId = paymentResponse.getSignatureId();

        // You can verify the payment here using Razorpay API if needed
        // For now, we will directly confirm the booking using the provided details
        Booking booking = bookingService.confimBooking(id,orderId, auth.getName(), paymentId);

        // Fetch the trip details for display
        ScheduleTrip scheduleTrip = scheduleTripService.getTripById(booking.getTripId());
        
        model.addAttribute("booking", booking);
        model.addAttribute("trip", scheduleTrip);
        System.out.println("Upadetd");
        return "book/success";  // The success page after payment
    }
    
    @GetMapping("/my-bookings")
    public String bookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getDetails());
        System.out.println(auth.getPrincipal());

     List<Booking> bookings = bookingService.findAllByUsername(auth.getName());
     for (Booking booking : bookings) {
	ScheduleTrip scheduleTrip =	scheduleTripService.getTripById(booking.getTripId());
	booking.setTrip(scheduleTrip);
	}
     List<Booking> sortedDate = bookings.stream().sorted(new DateComparator()).toList();
     List<Booking> sortedTime = bookings.stream().sorted(new TimeComparator()).toList();

     model.addAttribute("bookings", sortedTime);
     return "book/booking-list";
    }
    



}

class DateComparator implements Comparator<Booking>{

	@Override
	public int compare(Booking o1, Booking o2) {
		// TODO Auto-generated method stub
		return o2.getTrip().getTripDate().compareTo(o1.getTrip().getTripDate());
	}
	
}

class TimeComparator implements Comparator<Booking>{

	@Override
	public int compare(Booking o1, Booking o2) {
		// TODO Auto-generated method stub
		return o2.getTrip().getTripTime().compareTo(o1.getTrip().getTripTime());
	}
	
}

