package com.travel.web.model;
import java.time.LocalDate;

public class TripSearchCriteria {

    private LocalDate tripDate;
    private String fromDestination;
    private String toDestination;

    // Constructor
    public TripSearchCriteria(LocalDate tripDate, String fromDestination, String toDestination) {
        this.tripDate = tripDate;
        this.fromDestination = fromDestination;
        this.toDestination = toDestination;
    }

    // Getters and Setters
    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public String getFromDestination() {
        return fromDestination;
    }

    public void setFromDestination(String fromDestination) {
        this.fromDestination = fromDestination;
    }

    public String getToDestination() {
        return toDestination;
    }

    public void setToDestination(String toDestination) {
        this.toDestination = toDestination;
    }

    @Override
    public String toString() {
        return "TripSearchCriteria{" +
                "tripDate=" + tripDate +
                ", fromDestination='" + fromDestination + '\'' +
                ", toDestination='" + toDestination + '\'' +
                '}';
    }
}
