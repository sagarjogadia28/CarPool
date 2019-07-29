package com.example.carpool.modelClasses;

public class RequestedRideContent {

    private String departureAddress;
    private String departureCity;
    private String destinationAddress;
    private String destinationCity;
    private String departureTime;
    private String departureDate;
    private int seatsNeeded;
    private String userID;
    private String postingDate;

    public RequestedRideContent() {
    }


    public RequestedRideContent(
            String departureAddress,
            String departureCity,
            String destinationAddress,
            String destinationCity,
            String departureTime,
            String departureDate,
            int seatsNeeded,
            String userID,
            String postingDate) {
        this.departureAddress = departureAddress;
        this.departureCity = departureCity;
        this.destinationAddress = destinationAddress;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.seatsNeeded = seatsNeeded;
        this.userID = userID;
        this.postingDate = postingDate;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public int getSeatsNeeded() {
        return seatsNeeded;
    }

    public void setSeatsNeeded(int seatsNeeded) {
        this.seatsNeeded = seatsNeeded;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

}
