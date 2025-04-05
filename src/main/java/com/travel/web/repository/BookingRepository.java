package com.travel.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.travel.web.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

	public Booking findByBookingId(String bookingId);

	public Booking findByTransactionId(String transactionId); // Assuming transactionId is of type String

	public List<Booking> findByUsername(String username);

	public List<Booking> findBySeatNumber(int num);
	

}
