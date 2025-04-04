package com.travel.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travel.web.model.User;
import com.travel.web.repository.UserRepository;

@RestController
@RequestMapping("/api/User")
public class UserApi implements AbstractApi<ResponseEntity<?>,User> {
	
	@Autowired
	private UserRepository UserRepository;

	@Override
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll() {
		// TODO Auto-generated method stub
		List<User> Users = UserRepository.findAll();
		return new ResponseEntity(Users,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/createNew")
	public ResponseEntity<?> createNew(User object) {
		// TODO Auto-generated method stub
	//User book =	UserRepository.save(object);
	return new ResponseEntity("This is not valid way to book trip",HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/findById/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		User User = UserRepository.findById(id).get();
		return new ResponseEntity(User,HttpStatus.ACCEPTED);
	}

	@Override
	@GetMapping("/deleteById/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		// TODO Auto-generated method stub
		//UserRepository.deleteById(id);
		return new ResponseEntity("You cant delete Users using this way with User id "+id,HttpStatus.ACCEPTED);

	}

	@Override
	@GetMapping("/updateUser/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") Long id, User object) {
		// TODO Auto-generated method stub
		
		return new ResponseEntity("This feature yet to developed",HttpStatus.ACCEPTED);
	}

	
	}
