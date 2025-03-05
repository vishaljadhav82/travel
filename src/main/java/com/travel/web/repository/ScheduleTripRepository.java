package com.travel.web.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.web.model.ScheduleTrip;

@Repository
public interface ScheduleTripRepository extends JpaRepository<ScheduleTrip, Long> {
	List<ScheduleTrip> findByTripDate(LocalDate date);
}
