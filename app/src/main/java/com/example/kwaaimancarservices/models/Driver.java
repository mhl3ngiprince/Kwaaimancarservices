package com.example.kwaaimancarservices.models;

public class Driver {
    private String driverId;
    private String licenseNumber;
    private String vehicleModel;
    private String vehicleMake;
    private String vehicleYear;
    private String vehicleColor;
    private String licensePlate;
    private String vehicleRegistration;
    private DriverStatus status;
    private double currentLatitude;
    private double currentLongitude;
    private boolean isOnline;
    private boolean isAvailable;
    private String currentTripId;
    private int totalTripsCompleted;
    private double totalEarnings;
    private long lastLocationUpdate;
    private int maxPassengers;
    private boolean hasAirConditioner;
    private boolean hasWifi;
    private boolean acceptsCash;
    private boolean acceptsCard;
    private String emergencyContact;
    private String bankAccountDetails;
    private double commission; // Percentage taken by platform
    private long joinedDate;

    public enum DriverStatus {
        OFFLINE,
        ONLINE_AVAILABLE,
        ON_TRIP,
        BREAK,
        SUSPENDED
    }

    public Driver() {
        // Required for Firebase
    }

    public Driver(String driverId, String licenseNumber, String vehicleModel,
                  String vehicleMake, String vehicleYear, String vehicleColor,
                  String licensePlate) {
        this.driverId = driverId;
        this.licenseNumber = licenseNumber;
        this.vehicleModel = vehicleModel;
        this.vehicleMake = vehicleMake;
        this.vehicleYear = vehicleYear;
        this.vehicleColor = vehicleColor;
        this.licensePlate = licensePlate;
        this.status = DriverStatus.OFFLINE;
        this.isOnline = false;
        this.isAvailable = false;
        this.totalTripsCompleted = 0;
        this.totalEarnings = 0.0;
        this.maxPassengers = 4;
        this.hasAirConditioner = false;
        this.hasWifi = false;
        this.acceptsCash = true;
        this.acceptsCard = false;
        this.commission = 15.0; // Default 15% commission
        this.joinedDate = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleMake() {
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) {
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(String vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVehicleRegistration() {
        return vehicleRegistration;
    }

    public void setVehicleRegistration(String vehicleRegistration) {
        this.vehicleRegistration = vehicleRegistration;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public double getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public double getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCurrentTripId() {
        return currentTripId;
    }

    public void setCurrentTripId(String currentTripId) {
        this.currentTripId = currentTripId;
    }

    public int getTotalTripsCompleted() {
        return totalTripsCompleted;
    }

    public void setTotalTripsCompleted(int totalTripsCompleted) {
        this.totalTripsCompleted = totalTripsCompleted;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public long getLastLocationUpdate() {
        return lastLocationUpdate;
    }

    public void setLastLocationUpdate(long lastLocationUpdate) {
        this.lastLocationUpdate = lastLocationUpdate;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        this.maxPassengers = maxPassengers;
    }

    public boolean isHasAirConditioner() {
        return hasAirConditioner;
    }

    public void setHasAirConditioner(boolean hasAirConditioner) {
        this.hasAirConditioner = hasAirConditioner;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public boolean isAcceptsCash() {
        return acceptsCash;
    }

    public void setAcceptsCash(boolean acceptsCash) {
        this.acceptsCash = acceptsCash;
    }

    public boolean isAcceptsCard() {
        return acceptsCard;
    }

    public void setAcceptsCard(boolean acceptsCard) {
        this.acceptsCard = acceptsCard;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(String bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public long getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(long joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getFullVehicleInfo() {
        return vehicleColor + " " + vehicleYear + " " + vehicleMake + " " + vehicleModel;
    }

    public void updateLocation(double latitude, double longitude) {
        this.currentLatitude = latitude;
        this.currentLongitude = longitude;
        this.lastLocationUpdate = System.currentTimeMillis();
    }
}