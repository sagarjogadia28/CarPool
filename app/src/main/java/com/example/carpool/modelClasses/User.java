package com.example.carpool.modelClasses;

public class User {

    private String name;
    private String email;
    private String phone;
    private float rating;

    public User(String name, String email, String phone, float rating) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.rating = rating;
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
}
