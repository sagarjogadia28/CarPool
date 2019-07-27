package com.example.carpool.modelClasses;

public class User {

    private String name;
    private String email;
    private String phone;
    private float rating;
    private int totalRides;

    public User(String name, String email, String phone, float rating, int totalRides) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
        this.totalRides = totalRides;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


    public int getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(int totalRides) {
        this.totalRides = totalRides;
    }
}
