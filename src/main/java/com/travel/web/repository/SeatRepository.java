package com.travel.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.web.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{

}
