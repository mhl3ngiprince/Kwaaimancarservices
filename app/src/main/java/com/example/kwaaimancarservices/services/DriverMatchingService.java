package com.example.kwaaimancarservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kwaaimancarservices.rides.models.Location;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Service responsible for matching drivers with passengers based on proximity and availability
 */
public class DriverMatchingService extends Service {
    
    private static final String TAG = "DriverMatchingService";
    private final IBinder binder = new DriverMatchingBinder();
    
    // Mock list of available drivers
    private List<Driver> availableDrivers;
    private Map<String, PassengerRequest> activeRequests;
    
    // Interface for callbacks
    private MatchingCallback callback;
    
    public class DriverMatchingBinder extends Binder {
        public DriverMatchingService getService() {
            return DriverMatchingService.this;
        }
    }
    
    public interface MatchingCallback {
        void onDriverFound(Driver driver);
        void onDriverNotFound();
        void onMatchInProgress();
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        availableDrivers = new ArrayList<>();
        activeRequests = new HashMap<>();
        initializeMockDrivers();
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    /**
     * Set callback for matching events
     */
    public void setCallback(MatchingCallback callback) {
        this.callback = callback;
    }
    
    /**
     * Find the best available driver for a passenger request
     */
    public void findDriverForPassenger(String requestId, Location pickupLocation, Location destinationLocation) {
        Log.d(TAG, "Finding driver for request: " + requestId);
        
        if (callback != null) {
            callback.onMatchInProgress();
        }
        
        // Simulate search delay
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate network delay
                
                // Find the closest available driver
                Driver closestDriver = findClosestAvailableDriver(pickupLocation);
                
                if (closestDriver != null) {
                    // Assign the driver to the request
                    PassengerRequest request = new PassengerRequest(requestId, pickupLocation, destinationLocation, System.currentTimeMillis());
                    activeRequests.put(requestId, request);
                    
                    // Update driver status
                    closestDriver.setStatus(Driver.STATUS_OCCUPIED);
                    
                    // Notify via callback
                    if (callback != null) {
                        runOnUiThread(() -> callback.onDriverFound(closestDriver));
                    }
                } else {
                    if (callback != null) {
                        runOnUiThread(() -> callback.onDriverNotFound());
                    }
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "Driver matching thread interrupted", e);
            }
        }).start();
    }
    
    /**
     * Cancel a passenger request
     */
    public void cancelRequest(String requestId) {
        PassengerRequest request = activeRequests.remove(requestId);
        if (request != null) {
            // Free up any assigned driver
            // In a real app, this would notify the driver that the request was canceled
        }
    }
    
    /**
     * Find the closest available driver to the pickup location
     */
    private Driver findClosestAvailableDriver(Location pickupLocation) {
        if (availableDrivers.isEmpty()) {
            return null;
        }
        
        // Sort drivers by distance to pickup location
        Collections.sort(availableDrivers, (driver1, driver2) -> {
            double dist1 = calculateDistance(driver1.getCurrentLocation(), pickupLocation);
            double dist2 = calculateDistance(driver2.getCurrentLocation(), pickupLocation);
            return Double.compare(dist1, dist2);
        });
        
        // Return the first available driver (closest)
        for (Driver driver : availableDrivers) {
            if (driver.getStatus() == Driver.STATUS_AVAILABLE) {
                return driver;
            }
        }
        
        return null; // No available drivers found
    }
    
    /**
     * Calculate distance between two locations in meters
     */
    private double calculateDistance(Location loc1, Location loc2) {
        // Convert to LatLng for distance calculation
        LatLng latLng1 = new LatLng(loc1.getLatitude(), loc1.getLongitude());
        LatLng latLng2 = new LatLng(loc2.getLatitude(), loc2.getLongitude());
        
        float[] results = new float[1];
        android.location.Location.distanceBetween(
            latLng1.latitude, latLng1.longitude,
            latLng2.latitude, latLng2.longitude,
            results
        );
        
        return results[0]; // Distance in meters
    }
    
    /**
     * Initialize mock drivers for demonstration
     */
    private void initializeMockDrivers() {
        // Create some mock drivers near Cape Town area
        availableDrivers.add(new Driver("DRV001", "Thabo Mthembu", "+27 72 123 4567", 
            new Location("Thabo", "Cape Town CBD", -33.9249, 18.4241), 
            "Toyota Avanza - CA 123 RT", Driver.STATUS_AVAILABLE));
        
        availableDrivers.add(new Driver("DRV002", "Sipho Nkomo", "+27 73 987 6543", 
            new Location("Sipho", "V&A Waterfront", -33.9049, 18.4190), 
            "Honda Civic - CA 456 RT", Driver.STATUS_AVAILABLE));
        
        availableDrivers.add(new Driver("DRV003", "Zanele Dlamini", "+27 82 111 2222", 
            new Location("Zanele", "Green Point", -33.9129, 18.3975), 
            "Ford Ranger - CA 789 RT", Driver.STATUS_AVAILABLE));
        
        availableDrivers.add(new Driver("DRV004", "Mandla Khumalo", "+27 71 333 4444", 
            new Location("Mandla", "Observatory", -33.9610, 18.4672), 
            "VW Polo - CA 321 RT", Driver.STATUS_OCCUPIED)); // Busy
        
        availableDrivers.add(new Driver("DRV005", "Precious Molefe", "+27 79 555 6666", 
            new Location("Precious", "Rosebank", -33.9530, 18.4591), 
            "BMW X1 - CA 654 RT", Driver.STATUS_AVAILABLE));
    }
    
    /**
     * Add a driver to the available pool
     */
    public void addDriver(Driver driver) {
        availableDrivers.add(driver);
    }
    
    /**
     * Remove a driver from the available pool
     */
    public void removeDriver(String driverId) {
        availableDrivers.removeIf(driver -> driver.getId().equals(driverId));
    }
    
    /**
     * Update driver location
     */
    public void updateDriverLocation(String driverId, Location newLocation) {
        for (Driver driver : availableDrivers) {
            if (driver.getId().equals(driverId)) {
                driver.setCurrentLocation(newLocation);
                break;
            }
        }
    }
    
    /**
     * Run UI updates on main thread
     */
    private void runOnUiThread(Runnable runnable) {
        // In a service, we need to use the main looper to run UI updates
        android.os.Handler mainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        mainHandler.post(runnable);
    }
    
    /**
     * Model class for Driver
     */
    public static class Driver {
        public static final int STATUS_AVAILABLE = 0;
        public static final int STATUS_OCCUPIED = 1;
        public static final int STATUS_OFFLINE = 2;
        
        private String id;
        private String name;
        private String phoneNumber;
        private Location currentLocation;
        private String vehicleDetails;
        private int status;
        
        public Driver(String id, String name, String phoneNumber, Location currentLocation, String vehicleDetails, int status) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.currentLocation = currentLocation;
            this.vehicleDetails = vehicleDetails;
            this.status = status;
        }
        
        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
        
        public Location getCurrentLocation() { return currentLocation; }
        public void setCurrentLocation(Location currentLocation) { this.currentLocation = currentLocation; }
        
        public String getVehicleDetails() { return vehicleDetails; }
        public void setVehicleDetails(String vehicleDetails) { this.vehicleDetails = vehicleDetails; }
        
        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }
    }
    
    /**
     * Model class for Passenger Request
     */
    public static class PassengerRequest {
        private String requestId;
        private Location pickupLocation;
        private Location destinationLocation;
        private long timestamp;
        
        public PassengerRequest(String requestId, Location pickupLocation, Location destinationLocation, long timestamp) {
            this.requestId = requestId;
            this.pickupLocation = pickupLocation;
            this.destinationLocation = destinationLocation;
            this.timestamp = timestamp;
        }
        
        // Getters
        public String getRequestId() { return requestId; }
        public Location getPickupLocation() { return pickupLocation; }
        public Location getDestinationLocation() { return destinationLocation; }
        public long getTimestamp() { return timestamp; }
    }
}