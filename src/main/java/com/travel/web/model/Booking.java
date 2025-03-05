package com.travel.web.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger; // Reference to Passenger

    @Column(name = "from_location", nullable = false)
    private String fromLocation; // Source Location

    @Column(name = "destination_location", nullable = false)
    private String destinationLocation; // Destination Location

    @Column(name = "total_distance", nullable = false)
    private double totalDistance; // Total Distance in Km

    @Column(name = "transaction_id", unique = true, nullable = false)
    private String transactionId; // Unique Transaction ID

    @Column(name = "rupees_paid", nullable = false)
    private double rupeesPaid; // Amount Paid in Rupees
    private String stops;
    private int seatNumber;
    private String status;
    private String bookingId;
    private long tripId;
    private String username;
    @Transient
    private ScheduleTrip trip;
    
    private boolean fullyBookedSeat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		this.destinationLocation = destinationLocation;
	}

	public double getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public double getRupeesPaid() {
		return rupeesPaid;
	}

	public void setRupeesPaid(double rupeesPaid) {
		this.rupeesPaid = rupeesPaid;
	}

	public String getStops() {
		return stops;
	}

	public void setStops(String stops) {
		this.stops = stops;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public long getTripId() {
		return tripId;
	}

	public void setTripId(long tripId) {
		this.tripId = tripId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ScheduleTrip getTrip() {
		return trip;
	}

	public void setTrip(ScheduleTrip trip) {
		this.trip = trip;
	}

	public boolean isFullyBookedSeat() {
		return fullyBookedSeat;
	}

	public void setFullyBookedSeat(boolean fullyBookedSeat) {
		this.fullyBookedSeat = fullyBookedSeat;
	}
    
    
    
}
