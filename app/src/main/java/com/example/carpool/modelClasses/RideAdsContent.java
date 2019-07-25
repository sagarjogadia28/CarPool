package com.example.carpool.modelClasses;

import java.sql.Date;
import java.sql.Time;

public class RideAdsContent {

    private String departureCity;
    private String departureAddress;
    private String destinationCity;
    private Time departureTime;
    private Date departureDate;
    private int seatsAvailable;
    private String userID;
    private Date postingDate;

    public RideAdsContent(){

    }

    public RideAdsContent(String departureCity, String departureAddress, String destinationCity, Time departureTime, Date departureDate, int seatsAvailable, String userID, Date postingDate) {
        this.departureCity = departureCity;
        this.departureAddress = departureAddress;
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

    public String getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String departureAddress) {
        this.departureAddress = departureAddress;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
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

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }
}
