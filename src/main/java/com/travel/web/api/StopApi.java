package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.Stop;
import com.travel.web.repository.StopRepository;

@RestController
@RequestMapping("/api/Stop")
public class StopApi implements AbstractApi<ResponseEntity<?>,Stop> {
	
	@Autowired
	private StopRepository StopRepository;

	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<Stop> Stops = StopRepository.findAll();
		return new ResponseEntity(Stops,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(Stop object) {
		// TODO Auto-generated method stub
	//Stop book =	StopRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Stop Stop = StopRepository.findById(id).get();
		return new ResponseEntity(Stop,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//StopRepository.deleteById(id);
		return new ResponseEntity("You cant delete Stops using this way with Stop id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updateStop/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, Stop object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}

	
	}
