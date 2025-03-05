package com.travel.web.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


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

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	
	
    
}
