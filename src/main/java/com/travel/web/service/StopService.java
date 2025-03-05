package com.travel.web.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.web.model.Stop;
import com.travel.web.repository.StopRepository;

@Service
public class StopService {

    @Autowired
    private StopRepository stopRepository;

    public List<Stop> getAllStops() {
        return stopRepository.findAll();
    }

    public List<Stop> getStopsByRouteId(Long routeId) {
        return stopRepository.findByRouteId(routeId);
    }

    public Optional<Stop> getStopById(Long id) {
        return stopRepository.findById(id);
    }

    public Stop saveStop(Stop stop) {
        return stopRepository.save(stop);
    }

    public void deleteStop(Long id) {
        stopRepository.deleteById(id);
    }
}
