package com.travel.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.web.model.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {
}
