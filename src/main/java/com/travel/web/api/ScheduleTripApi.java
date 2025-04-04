package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.ScheduleTrip;
import com.travel.web.repository.ScheduleTripRepository;

@RestController
@RequestMapping("/api/ScheduleTrip")
public class ScheduleTripApi implements AbstractApi<ResponseEntity<?>,ScheduleTrip> {
	
	@Autowired
	private ScheduleTripRepository ScheduleTripRepository;

	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<ScheduleTrip> ScheduleTrips = ScheduleTripRepository.findAll();
		return new ResponseEntity(ScheduleTrips,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(ScheduleTrip object) {
		// TODO Auto-generated method stub
	//ScheduleTrip book =	ScheduleTripRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		ScheduleTrip ScheduleTrip = ScheduleTripRepository.findById(id).get();
		return new ResponseEntity(ScheduleTrip,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//ScheduleTripRepository.deleteById(id);
		return new ResponseEntity("You cant delete ScheduleTrips using this way with ScheduleTrip id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updateScheduleTrip/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, ScheduleTrip object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}

	
	}
