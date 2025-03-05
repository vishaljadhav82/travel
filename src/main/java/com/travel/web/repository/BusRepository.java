package com.travel.web.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.web.model.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {
}
