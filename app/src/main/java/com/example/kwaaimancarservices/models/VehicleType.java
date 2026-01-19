package com.example.kwaaimancarservices.models;

public class VehicleType {
    private String name;
    private String description;
    private String priceRange;
    private String eta;
    private int iconResource;
    private boolean available;
    private boolean selected;

    public VehicleType() {
        // Empty constructor for Firebase
    }

    public VehicleType(String name, String description, String priceRange, String eta, int iconResource, boolean available) {
        this.name = name;
        this.description = description;
        this.priceRange = priceRange;
        this.eta = eta;
        this.iconResource = iconResource;
        this.available = available;
        this.selected = false;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public String getEta() {
        return eta;
    }

    public int getIconResource() {
        return iconResource;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isSelected() {
        return selected;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return name + " - " + priceRange;
    }
}