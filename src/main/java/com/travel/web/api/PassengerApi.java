package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.Passenger;
import com.travel.web.repository.PassengerRepository;

@RestController
@RequestMapping("/api/Passenger")
public class PassengerApi implements AbstractApi<ResponseEntity<?>,Passenger> {
	
	@Autowired
	private PassengerRepository PassengerRepository;

	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<Passenger> Passengers = PassengerRepository.findAll();
		return new ResponseEntity(Passengers,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(Passenger object) {
		// TODO Auto-generated method stub
	//Passenger book =	PassengerRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Passenger Passenger = PassengerRepository.findById(id).get();
		return new ResponseEntity(Passenger,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//PassengerRepository.deleteById(id);
		return new ResponseEntity("You cant delete Passengers using this way with Passenger id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updatePassenger/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, Passenger object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}

	
	}
