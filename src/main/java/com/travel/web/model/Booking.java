package com.travel.web.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
    
}
