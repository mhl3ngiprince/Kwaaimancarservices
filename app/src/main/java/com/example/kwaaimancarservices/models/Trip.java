package com.example.kwaaimancarservices.models;

public class Trip {
    private String date;
    private String price;
    private String pickupLocation;
    private String dropoffLocation;
    private String distance;
    private String duration;

    public Trip(String date, String price, String pickupLocation, String dropoffLocation, String distance, String duration) {
        this.date = date;
        this.price = price;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.distance = distance;
        this.duration = duration;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public String getDistance() {
        return distance;
    }

    public String getDuration() {
        return duration;
    }

    // Setters
    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}