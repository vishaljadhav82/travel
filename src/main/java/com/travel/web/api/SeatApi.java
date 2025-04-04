package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.Seat;
import com.travel.web.repository.SeatRepository;

@RestController
@RequestMapping("/api/Seat")
public class SeatApi implements AbstractApi<ResponseEntity<?>,Seat> {
	
	@Autowired
	private SeatRepository SeatRepository;

	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<Seat> Seats = SeatRepository.findAll();
		return new ResponseEntity(Seats,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(Seat object) {
		// TODO Auto-generated method stub
	//Seat book =	SeatRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Seat Seat = SeatRepository.findById(id).get();
		return new ResponseEntity(Seat,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//SeatRepository.deleteById(id);
		return new ResponseEntity("You cant delete Seats using this way with Seat id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updateSeat/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, Seat object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}

	
	}
