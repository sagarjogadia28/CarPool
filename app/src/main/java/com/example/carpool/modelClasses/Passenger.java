package com.example.carpool.modelClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Passenger implements Parcelable {

    private String departureAddress;
    private String departureCity;
    private String destinationAddress;
    private String destinationCity;
    private String departureTime;
    private String departureDate;
    private int seatsNeeded;
    private String userID;

    protected Passenger(Parcel in) {
        departureAddress = in.readString();
        departureCity = in.readString();
        destinationAddress = in.readString();
        destinationCity = in.readString();
        departureTime = in.readString();
        departureDate = in.readString();
        seatsNeeded = in.readInt();
        userID = in.readString();
        driverID = in.readString();
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(Parcel in) {
            return new Passenger(in);
        }

        @Override
        public Passenger[] newArray(int size) {
            return new Passenger[size];
        }
    };

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

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    private String driverID;

    public Passenger() {
    }

    public Passenger(String departureAddress, String departureCity, String destinationAddress, String destinationCity, String departureDate, String departureTime, int seatsNeeded, String userID, String driverID) {
        this.departureAddress = departureAddress;
        this.departureCity = departureCity;
        this.destinationAddress = destinationAddress;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.seatsNeeded = seatsNeeded;
        this.userID = userID;
        this.driverID = driverID;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(departureAddress);
        parcel.writeString(departureCity);
        parcel.writeString(destinationAddress);
        parcel.writeString(destinationCity);
        parcel.writeString(departureTime);
        parcel.writeString(departureDate);
        parcel.writeInt(seatsNeeded);
        parcel.writeString(userID);
        parcel.writeString(driverID);
    }
}
