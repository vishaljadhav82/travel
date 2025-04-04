package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.Route;
import com.travel.web.repository.RouteRepository;

@RestController
@RequestMapping("/api/Route")
public class RouteApi implements AbstractApi<ResponseEntity<?>,Route> {
	
	@Autowired
	private RouteRepository RouteRepository;

	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<Route> Routes = RouteRepository.findAll();
		return new ResponseEntity(Routes,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(Route object) {
		// TODO Auto-generated method stub
	//Route book =	RouteRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		Route Route = RouteRepository.findById(id).get();
		return new ResponseEntity(Route,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//RouteRepository.deleteById(id);
		return new ResponseEntity("You cant delete Routes using this way with Route id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updateRoute/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, Route object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}

	
	}
