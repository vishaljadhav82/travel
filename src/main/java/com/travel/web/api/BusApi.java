package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.Bus;
import com.travel.web.repository.BusRepository;

@RestController
@RequestMapping("/api/bus")
public class BusApi implements AbstractApi<ResponseEntity<?>, Bus>{

	@Autowired
	BusRepository busRepository;
	
	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<Bus> buses = busRepository.findAll();
		return new ResponseEntity(buses,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(Bus object) {
		// TODO Auto-generated method stub
		Bus bus = busRepository.save(object);
		return new ResponseEntity(bus,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
	Bus bus =	busRepository.findById(id).get();
	return new ResponseEntity(bus,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteBusById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//busRepository.deleteById(id);
		return new ResponseEntity("This service yet to developed",HttpStatus.ACCEPTED);
	}

	@Override
	public ResponseEntity<?> updateById(Long id, Bus object) {
		// TODO Auto-generated method stub
		return new ResponseEntity("This service yet to developed",HttpStatus.ACCEPTED);
	}

}
