package com.travel.web.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.web.model.Passenger;
import com.travel.web.model.User;
import com.travel.web.repository.PassengerRepository;
import com.travel.web.repository.UserRepository;

@Service
public class PassengerService  {

    @Autowired
    private PassengerRepository passengerRepository;
    @Autowired
    private UserRepository repository;

    
    public User savePassenger(Passenger passenger,String username) {
    User user =	repository.findByUsername(username).get();
    user.getPassengers().add(passenger);
        return repository.save(user);
    }

    
    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }
    
    public List<Passenger> getAllPassengersByUsername(String username) {
    	
        return repository.findByUsername(username).get().getPassengers();
    }

    
    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepository.findById(id);
    }

    
    public Passenger updatePassenger(Long id, Passenger passenger,String username) {
    	Passenger	existingPassenger =	passengerRepository.findById(id).get();
            existingPassenger.setFirstName(passenger.getFirstName());
            existingPassenger.setLastName(passenger.getLastName());
            existingPassenger.setEmail(passenger.getEmail());
            existingPassenger.setPhoneNumber(passenger.getPhoneNumber());
            existingPassenger.setSeatAllotted(false);
            existingPassenger.setAge(passenger.getAge());
            Passenger saved = passengerRepository.save(existingPassenger);
            
            System.out.println("Inside Service");
            return saved;
    }

    
    public void deletePassenger(Long id) {
        passengerRepository.deleteById(id);
    }
}
