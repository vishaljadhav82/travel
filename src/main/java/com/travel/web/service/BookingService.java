package com.travel.web.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.travel.web.model.Booking;
import com.travel.web.model.Bus;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Seat;
import com.travel.web.model.Stop;
import com.travel.web.repository.BookingRepository;
import com.travel.web.repository.ScheduleTripRepository;
import com.travel.web.repository.SeatRepository;
import com.travel.web.repository.UserRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;
	@Value("${razorpay.key.id}")
	private String razorPayKey;
	@Value("${razorpay.secret.key}")
	private String razorSecret;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ScheduleTripRepository scheduleTripRepository;
	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private EmailService emailService;

	private RazorpayClient client;

	public Booking creatBooking(Booking booking, String username) throws Exception {

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", booking.getRupeesPaid() * 100);
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", booking.getPassenger().getEmail());

		this.client = new RazorpayClient(razorPayKey, razorSecret);
		Order order = client.orders.create(orderRequest);
		booking.setTransactionId(order.get("id"));
		booking.setStatus(order.get("status"));
		booking.setUsername(username);
		bookingRepository.save(booking);
		System.out.println("Completed");
		return booking;
	}

	public Booking confimBooking(long id, String orderId, String username, String paymentId) {
		Booking booking = bookingRepository.findByTransactionId(orderId);
		List<Booking> listBooking = bookingRepository.findBySeatNumber(booking.getSeatNumber());
		System.out.println(booking);
		// booking.setBookingId(bookingId);

		booking.setStatus("COMPLETED");
		booking.setBookingId(paymentId);
		ScheduleTrip scheduleTrip = scheduleTripRepository.findById(booking.getTripId()).get();
		scheduleTrip.setAvailableSeats(scheduleTrip.getAvailableSeats() - 1);
		scheduleTrip.setBookedSeats(scheduleTrip.getBookedSeats() + 1);
		List<Stop> stops = scheduleTrip.getRoute().getStops().stream()
				.sorted(Comparator.comparing(Stop::getDistanceFromStart)).toList();

		List<String> stopsNames = new ArrayList<String>();
		for (Stop name : stops) {
			stopsNames.add(name.getStopName());
		}
		int firstIndex = stopsNames.indexOf(booking.getFromLocation());
		int lastIndex = stopsNames.indexOf(booking.getDestinationLocation());
		String bookedStops = stopsNames.get(firstIndex) + ",";

		for (int i = firstIndex + 1; i < lastIndex; i++) {
			bookedStops += stopsNames.get(i) + ",";
		}
		bookedStops += stopsNames.get(lastIndex);
		booking.setStops(bookedStops);
		Booking newBooking = bookingRepository.save(booking);
		scheduleTrip.getBookings().add(newBooking);
		scheduleTripRepository.save(scheduleTrip);
		String toEmail = newBooking.getPassenger().getEmail();

		String subject = "Booking Confirmation - " + newBooking.getBookingId();

		String htmlBody = 
			    "<html>" +
			    "<body style='font-family: Arial, sans-serif; color: #333;'>" +
			    "<h2 style='color: #2E86C1;'>Dear " + newBooking.getPassenger().getFirstName() + " " + newBooking.getPassenger().getLastName() + ",</h2>" +
			    "<p>Thank you for booking your trip with <strong>TravelWith</strong>! 🚍</p>" +
			    "<p>Here are your booking details:</p>" +
			    
			    "<table style='width: 100%; border-collapse: collapse;'>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Booking ID:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getBookingId() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Passenger Name:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getPassenger().getFirstName() + " " + newBooking.getPassenger().getLastName() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>From:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getFromLocation() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>To:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getDestinationLocation() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Stops:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getStops() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Seat Number:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getSeatNumber() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Total Distance:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getTotalDistance() + " Km</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Amount Paid:</td><td style='padding: 8px; border: 1px solid #ddd;'>₹" + newBooking.getRupeesPaid() + "</td></tr>" +
			    "  <tr><td style='padding: 8px; border: 1px solid #ddd;'>Status:</td><td style='padding: 8px; border: 1px solid #ddd;'>" + newBooking.getStatus() + "</td></tr>" +
			    "</table>" +

			    "<p style='margin-top: 20px;'>We look forward to serving you! 🌟</p>" +
			    "<p>-- <strong>TravelWith Team</strong></p>" +
			    "</body>" +
			    "</html>";

		emailService.sendEmail(toEmail, subject, htmlBody);
		return newBooking;

	}

	public List<Booking> findAllByUsername(String username) {
		return bookingRepository.findByUsername(username);
	}

	public int checkAvailability(String from, String to, Long id) {
		int seatNumber = -1;
		String stopString = "";

		ScheduleTrip scheduleTrip = scheduleTripRepository.findById(id).orElse(null);
		if (scheduleTrip == null) {
			return seatNumber; // No trip found
		}

		List<Stop> stopsRoute = scheduleTrip.getRoute().getStops().stream()
				.sorted(Comparator.comparing(Stop::getDistanceFromStart)).toList();

		// Find indices of from & to stops
		int f = -1, l = -1;
		for (int i = 0; i < stopsRoute.size(); i++) {
			if (stopsRoute.get(i).getStopName().equals(from)) {
				f = i;
			}
			if (stopsRoute.get(i).getStopName().equals(to)) {
				l = i;
			}
		}

		// Validate the stop indices
		if (f == -1 || l == -1 || f > l) {
			return seatNumber; // Invalid stops
		}

		List<String> userStops = new ArrayList<>();
		for (int i = f; i <= l; i++) {
			userStops.add(stopsRoute.get(i).getStopName());
			stopString += stopsRoute.get(i).getStopName() + ",";
		}

		// Get bus seats
		Bus bus = scheduleTrip.getBus();
		List<Seat> seats = bus.getBusSeats();

		// Find an available seat
		for (Seat seat : seats) {
			String currentStops = seat.getStops();
			if (currentStops == null || currentStops.isEmpty()) {
				// If seat has no assigned stops, allocate it to the user
				seat.setStops(stopString);
				seatNumber = seat.getSeatNumber();
				seatRepository.save(seat);
				return seatNumber;
			}

			// Split stops and check if there's an overlap
			String[] seatStops = currentStops.split(",");
			boolean hasOverlap = false;
			for (String userStop : userStops) {
				for (String stop : seatStops) {
					if (stop.equals(userStop)) {
						hasOverlap = true;
						break;
					}
				}
				if (hasOverlap)
					break;
			}

			// If no overlap, assign the seat
			if (!hasOverlap) {
				seat.setStops(currentStops + stopString);
				seatNumber = seat.getSeatNumber();
				seatRepository.save(seat);
				return seatNumber;
			}
		}

		return seatNumber; // No available seat found
	}

}
