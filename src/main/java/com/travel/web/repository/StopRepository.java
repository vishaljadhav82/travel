package com.travel.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.travel.web.model.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {
    List<Stop> findByRouteId(Long routeId);

}
