package com.travel.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travel.web.model.Route;
import com.travel.web.model.Stop;
import com.travel.web.repository.RouteRepository;
import com.travel.web.repository.StopRepository;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StopRepository stopRepository;

    // Create a new Route
    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

    // Get all Routes
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    // Get Route by ID
    public Optional<Route> getRouteById(Long id) {
        return routeRepository.findById(id);
    }

    // Update an existing Route
    public Route updateRoute(Long id, Route updatedRoute) {
        if (routeRepository.existsById(id)) {
            updatedRoute.setId(id);
            return routeRepository.save(updatedRoute);
        }
        return null; // Or throw an exception
    }

    // Delete a Route by ID
    public boolean deleteRoute(Long id) {
        if (routeRepository.existsById(id)) {
            routeRepository.deleteById(id);
            return true;
        }
        return false; // Or throw an exception
    }

    // Create a new Stop for a specific Route
    public Stop addStopToRoute(Long routeId, Stop stop) {
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        if (routeOptional.isPresent()) {
            Route route = routeOptional.get();
            stop.setRoute(route);
            return stopRepository.save(stop);
        }
        return null; // Or throw an exception
    }

    // Get all Stops for a specific Route
    public List<Stop> getStopsForRoute(Long routeId) {
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        if (routeOptional.isPresent()) {
            return routeOptional.get().getStops();
        }
        return null; // Or throw an exception
    }

    // Delete a Stop by ID
    public boolean deleteStop(Long stopId) {
        if (stopRepository.existsById(stopId)) {
            stopRepository.deleteById(stopId);
            return true;
        }
        return false; // Or throw an exception
    }
}
