package com.travel.web.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.travel.web.model.Bus;
import com.travel.web.model.ScheduleTrip;
import com.travel.web.model.Stop;
import com.travel.web.repository.BusRepository;
import com.travel.web.repository.ScheduleTripRepository;

@Service
public class ScheduleTripService {

    @Autowired
    private ScheduleTripRepository scheduleTripRepository;
    @Autowired
    private BusRepository busRepository;

    // Get all scheduled trips
    public List<ScheduleTrip> getAllTrips() {
        return scheduleTripRepository.findAll();
    }

    // Get a trip by ID
    public ScheduleTrip getTripById(Long id) {
        return scheduleTripRepository.findById(id).orElse(null);
    }

    // Save a new trip
    public ScheduleTrip saveTrip(ScheduleTrip trip) {
    	Bus bus = busRepository.findById(trip.getBus().getId()).get();
    	bus.setAvailable(false);
  
        return scheduleTripRepository.save(trip);
    }

    // Update a trip
    public ScheduleTrip updateTrip(Long id, ScheduleTrip tripDetails) {
    	System.out.println("Inside Trip Updation");
        Optional<ScheduleTrip> optionalTrip = scheduleTripRepository.findById(id);
        
        if (optionalTrip.isPresent()) {
            ScheduleTrip trip = optionalTrip.get();
            if(tripDetails.getTripEnd() == 1) {
        Bus bus =	busRepository.findById(trip.getId()).get();
            bus.setAvailable(true);
            busRepository.save(bus);
            }
            trip.setAvailableSeats(tripDetails.getAvailableSeats());
            trip.setCostPerKm(tripDetails.getCostPerKm());
            trip.setTripEnd(tripDetails.getTripEnd());
            trip.setTripTime(trip.getTripTime());
            trip.setTripDate(tripDetails.getTripDate());
            return scheduleTripRepository.save(trip);
        } else {
            return null;
        }
    }

    // Delete a trip
    public void deleteTrip(Long id) {
        scheduleTripRepository.deleteById(id);
    }
    
    // Search trips by date, from-to destination, and stops in the route
    public List<ScheduleTrip> searchTrips(LocalDate tripDate, String fromDestination, String toDestination) {
        List<ScheduleTrip> allTrips = scheduleTripRepository.findByTripDate(tripDate);
        List<ScheduleTrip> filteredTrips = new ArrayList<ScheduleTrip>();
        for (int i = 0; i < allTrips.size(); i++) {
        	List<Stop> stops = allTrips.get(i).getRoute().getStops();
         outerLoop:	for(int j = 0; j < stops.size();j++) {
        		if ((stops.get(j).getStopName()).equals(fromDestination)) {
					for(int k = j + 1; k < stops.size();k++) {
		        		if ((stops.get(k).getStopName()).equals(toDestination)) {
		        		
		        			filteredTrips.add(allTrips.get(i));
		        			break outerLoop;
		        		}

					}
					
				}
        	}
		}
        
		return filteredTrips;  
    }
    
    
}
