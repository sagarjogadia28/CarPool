package com.example.carpool.modelClasses;

import java.sql.Date;
import java.sql.Time;

public class MyRidesContent {

    private String departureCity;
    private String departureAddress;
    private String destinationCity;
    private Time departureTime;
    private Date departureDate;
    private int noOfseats;

    public MyRidesContent(String departureCity, String departureAddress, String destinationCity, Time departureTime, Date departureDate, int noOfseats) {
        this.departureCity = departureCity;
        this.departureAddress = departureAddress;
        this.destinationCity = destinationCity;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.noOfseats = noOfseats;
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

    public int getNoOfseats() {
        return noOfseats;
    }

    public void setNoOfseats(int noOfseats) {
        this.noOfseats = noOfseats;
    }
}
