package com.travel.web.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.Booking;
import com.travel.web.model.Bus;
import com.travel.web.model.Route;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Seat;
import com.travel.web.model.Stop;
import com.travel.web.repository.BookingRepository;
import com.travel.web.repository.BusRepository;
import com.travel.web.repository.ScheduleTripRepository;
import com.travel.web.repository.SeatRepository;
import com.travel.web.service.RouteService;
import com.travel.web.service.StopService;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin("*")
public class BookingApi implements AbstractApi<ResponseEntity<?>,Booking> {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private ScheduleTripRepository scheduleTripRepository;
	@Autowired
	private  BusRepository busRepository;
    @Autowired
    private RouteService routeService;

    @Autowired
    private StopService stopService;
    @Autowired
    private SeatRepository seatRepository;
        


	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<Booking> bookings = bookingRepository.findAll();
				return new ResponseEntity(bookings,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(Booking object) {
		// TODO Auto-generated method stub
	//Booking book =	bookingRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Booking booking = bookingRepository.findById(id).get();
		return new ResponseEntity(booking,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//bookingRepository.deleteById(id);
		return new ResponseEntity("You cant delete bookings using this way with booking id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updateBooking/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, Booking object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}
   
	@GetMapping("/dashbourd")
    public ResponseEntity<?> dashboaurd(){
    List<Bus> buses =	busRepository.findAll();
   List<Route> routes = routeService.getAllRoutes();
   List<Stop> stops = stopService.getAllStops();
   List<Seat> seats = seatRepository.findAll();
   List<Booking> bookings = bookingRepository.findAll();
   List<ScheduleTrip> scheduleTrips = scheduleTripRepository.findAll();
   Map<String,List<?>> map = new HashMap<>();
  // map.put("buses", buses);
   map.put("routes", routes);
  // map.put("stops", stops);
   map.put("seats", seats);
   map.put("bookings", bookings);
   map.put("scheduleTrips", scheduleTrips);
   map.put("Test", List.of("hi","hdh"));
    	return new ResponseEntity(map,HttpStatus.ACCEPTED);
    }
	
	}
