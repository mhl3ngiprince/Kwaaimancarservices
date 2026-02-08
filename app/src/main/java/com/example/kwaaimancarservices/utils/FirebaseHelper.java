package com.example.kwaaimancarservices.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kwaaimancarservices.rides.models.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to manage Firebase operations for the Kwaaiman app
 */
public class FirebaseHelper {
    
    private static final String TAG = "FirebaseHelper";
    
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    
    // Collection names
    private static final String USERS_COLLECTION = "users";
    private static final String TRIPS_COLLECTION = "trips";
    private static final String DRIVERS_COLLECTION = "drivers";
    private static final String PAYMENTS_COLLECTION = "payments";
    
    public FirebaseHelper() {
        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }
    
    /**
     * Save user profile to Firestore
     */
    public Task<Void> saveUserProfile(String userId, String name, String email, String phone) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("phone", phone);
        user.put("createdAt", System.currentTimeMillis());
        
        return db.collection(USERS_COLLECTION)
                .document(userId)
                .set(user);
    }
    
    /**
     * Save trip information to Firestore
     */
    public Task<DocumentReference> saveTrip(String userId, Location pickup, Location destination, 
                                          double fare, String driverId) {
        Map<String, Object> trip = new HashMap<>();
        trip.put("userId", userId);
        trip.put("driverId", driverId);
        trip.put("pickupLocation", pickup.toMap());
        trip.put("destinationLocation", destination.toMap());
        trip.put("fare", fare);
        trip.put("status", "requested"); // requested, accepted, in_progress, completed, cancelled
        trip.put("timestamp", System.currentTimeMillis());
        
        return db.collection(TRIPS_COLLECTION).add(trip);
    }
    
    /**
     * Update trip status
     */
    public Task<Void> updateTripStatus(String tripId, String status) {
        Map<String, Object> update = new HashMap<>();
        update.put("status", status);
        update.put("updatedAt", System.currentTimeMillis());
        
        return db.collection(TRIPS_COLLECTION)
                .document(tripId)
                .update(update);
    }
    
    /**
     * Get user's trip history
     */
    public Task<QuerySnapshot> getUserTripHistory(String userId) {
        return db.collection(TRIPS_COLLECTION)
                .whereEqualTo("userId", userId)
                .orderBy("timestamp")
                .get();
    }
    
    /**
     * Save driver information
     */
    public Task<Void> saveDriverInfo(String driverId, String name, String phone, 
                                   String vehicleModel, String vehiclePlate, double rating) {
        Map<String, Object> driver = new HashMap<>();
        driver.put("name", name);
        driver.put("phone", phone);
        driver.put("vehicleModel", vehicleModel);
        driver.put("vehiclePlate", vehiclePlate);
        driver.put("rating", rating);
        driver.put("available", true);
        driver.put("lastSeen", System.currentTimeMillis());
        
        return db.collection(DRIVERS_COLLECTION)
                .document(driverId)
                .set(driver);
    }
    
    /**
     * Update driver location
     */
    public Task<Void> updateDriverLocation(String driverId, Location location) {
        Map<String, Object> update = new HashMap<>();
        update.put("currentLocation", location.toMap());
        update.put("lastUpdated", System.currentTimeMillis());
        
        return db.collection(DRIVERS_COLLECTION)
                .document(driverId)
                .update(update);
    }
    
    /**
     * Set driver availability status
     */
    public Task<Void> setDriverAvailability(String driverId, boolean available) {
        Map<String, Object> update = new HashMap<>();
        update.put("available", available);
        update.put("lastUpdated", System.currentTimeMillis());
        
        return db.collection(DRIVERS_COLLECTION)
                .document(driverId)
                .update(update);
    }
    
    /**
     * Get nearby available drivers
     */
    public Task<QuerySnapshot> getNearbyAvailableDrivers(Location location, double radiusInKm) {
        // In a real implementation, you would use geohashes or geofire to find nearby drivers
        // This is a simplified version
        return db.collection(DRIVERS_COLLECTION)
                .whereEqualTo("available", true)
                .get();
    }
    
    /**
     * Save payment information
     */
    public Task<DocumentReference> savePayment(String tripId, String userId, double amount, 
                                            String paymentMethod, String status) {
        Map<String, Object> payment = new HashMap<>();
        payment.put("tripId", tripId);
        payment.put("userId", userId);
        payment.put("amount", amount);
        payment.put("paymentMethod", paymentMethod);
        payment.put("status", status);
        payment.put("timestamp", System.currentTimeMillis());
        
        return db.collection(PAYMENTS_COLLECTION).add(payment);
    }
    
    /**
     * Get current authenticated user ID
     */
    public String getCurrentUserId() {
        if (auth.getCurrentUser() != null) {
            return auth.getCurrentUser().getUid();
        }
        return null;
    }
    
    /**
     * Check if user is authenticated
     */
    public boolean isUserAuthenticated() {
        return auth.getCurrentUser() != null;
    }
    
    /**
     * Sign out user
     */
    public void signOut() {
        auth.signOut();
    }
    
    /**
     * Get Firestore database instance
     */
    public FirebaseFirestore getDb() {
        return db;
    }
    
    /**
     * Get auth instance
     */
    public FirebaseAuth getAuth() {
        return auth;
    }
    
    /**
     * Add trip status change listener
     */
    public void addTripStatusListener(String tripId, TripStatusCallback callback) {
        db.collection(TRIPS_COLLECTION)
                .document(tripId)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen failed.", e);
                        return;
                    }
                    
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        String status = documentSnapshot.getString("status");
                        if (callback != null) {
                            callback.onStatusChanged(status);
                        }
                    }
                });
    }
    
    /**
     * Interface for trip status callback
     */
    public interface TripStatusCallback {
        void onStatusChanged(String status);
    }
}