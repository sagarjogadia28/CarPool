package com.example.carpool.modelClasses;

import java.sql.Date;
import java.sql.Time;

public class RideAdsContent {

    private String departureCity;
    private String destinationCity;
    private String departureTime;
    private String departureDate;
    private int seatsAvailable;
    private String userID;
    private String postingDate;

    public RideAdsContent(){

    }

    public RideAdsContent(String departureCity, String destinationCity, String departureTime, String departureDate, int seatsAvailable, String userID, String postingDate) {
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.seatsAvailable = seatsAvailable;
        this.userID = userID;
        this.postingDate = postingDate;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
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

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
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
