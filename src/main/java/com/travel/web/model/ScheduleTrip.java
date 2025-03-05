package com.travel.web.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "schedule_trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
}
