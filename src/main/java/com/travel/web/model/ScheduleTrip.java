package com.travel.web.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "schedule_trips")
public class ScheduleTrip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus; // Reference to Bus

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route; // Reference to Route

    @OneToMany
    private List<Booking> bookings;
    
    @Column(name = "booked_seats", nullable = false)
    private int bookedSeats; // Number of booked seats

    @Column(name = "available_seats", nullable = false)
    private int availableSeats; // Number of available seats

    @Column(name = "cost_per_km", nullable = false)
    private double costPerKm; // Cost per kilometer

    @Column(name = "trip_date", nullable = false)
    private LocalDate tripDate; // New: Trip Date

    @Column(name = "trip_time", nullable = false)
    private LocalTime tripTime; // New: Trip Time
    
    private int tripEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public int getBookedSeats() {
		return bookedSeats;
	}

	public void setBookedSeats(int bookedSeats) {
		this.bookedSeats = bookedSeats;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public double getCostPerKm() {
		return costPerKm;
	}

	public void setCostPerKm(double costPerKm) {
		this.costPerKm = costPerKm;
	}

	public LocalDate getTripDate() {
		return tripDate;
	}

	public void setTripDate(LocalDate tripDate) {
		this.tripDate = tripDate;
	}

	public LocalTime getTripTime() {
		return tripTime;
	}

	public void setTripTime(LocalTime tripTime) {
		this.tripTime = tripTime;
	}

	public int getTripEnd() {
		return tripEnd;
	}

	public void setTripEnd(int tripEnd) {
		this.tripEnd = tripEnd;
	}
    
    
}
