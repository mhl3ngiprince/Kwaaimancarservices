package com.example.kwaaimancarservices.models;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String profileImageUrl;
    private UserType userType;
    private double rating;
    private int totalRides;
    private boolean isVerified;
    private String studentId; // For student verification
    private String institutionName; // University/College name
    private long createdAt;
    private long lastActive;

    public enum UserType {
        STUDENT, DRIVER, ADMIN
    }

    public User() {
        // Required for Firebase
    }

    public User(String userId, String firstName, String lastName, String email,
                String phoneNumber, UserType userType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.rating = 0.0;
        this.totalRides = 0;
        this.isVerified = false;
        this.createdAt = System.currentTimeMillis();
        this.lastActive = System.currentTimeMillis();
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalRides() {
        return totalRides;
    }

    public void setTotalRides(int totalRides) {
        this.totalRides = totalRides;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastActive() {
        return lastActive;
    }

    public void setLastActive(long lastActive) {
        this.lastActive = lastActive;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}