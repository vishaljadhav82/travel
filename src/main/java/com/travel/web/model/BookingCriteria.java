package com.travel.web.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookingCriteria {

    private String fromLocation; // Source Location
    private String destinationLocation; // Destination Location
    private Long passengerId; // Passenger ID
    private Long tripId;

    public BookingCriteria() {
        // Default constructor
    }

    public BookingCriteria(String fromLocation, String destinationLocation, Long passengerId,Long tripId) {
        this.fromLocation = fromLocation;
        this.destinationLocation = destinationLocation;
        this.passengerId = passengerId;
        this.tripId = tripId;
    }

	@Override
	public String toString() {
		return "BookingCriteria [fromLocation=" + fromLocation + ", destinationLocation=" + destinationLocation
				+ ", passengerId=" + passengerId + ", tripId=" + tripId + "]";
	}
    
}
