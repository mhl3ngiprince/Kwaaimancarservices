package com.example.kwaaimancarservices.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.kwaaimancarservices.rides.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Security service to handle safety features for the Kwaaiman app
 */
public class SecurityService extends Service {
    
    private static final String TAG = "SecurityService";
    private final IBinder binder = new SecurityBinder();
    
    private List<String> emergencyContacts;
    private boolean tripSharingActive = false;
    private String sharedTripId;
    
    public class SecurityBinder extends Binder {
        public SecurityService getService() {
            return SecurityService.this;
        }
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        emergencyContacts = new ArrayList<>();
        Log.d(TAG, "Security service created");
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    /**
     * Activate emergency SOS
     */
    public void activateEmergencySOS() {
        Log.w(TAG, "EMERGENCY SOS ACTIVATED!");
        
        // In a real app, this would:
        // 1. Send alerts to emergency contacts
        // 2. Share real-time location with authorities
        // 3. Record audio/video evidence
        // 4. Send alerts to central monitoring station
        
        // For demo purposes, just log and show toast
        Toast.makeText(this, "EMERGENCY SOS ACTIVATED! Help is on the way.", Toast.LENGTH_LONG).show();
        
        // Alert emergency contacts
        alertEmergencyContacts("EMERGENCY: SOS activated by user. Location: " + getCurrentLocation());
    }
    
    /**
     * Add an emergency contact
     */
    public void addEmergencyContact(String contactName, String contactNumber) {
        String contact = contactName + ": " + contactNumber;
        emergencyContacts.add(contact);
        Log.d(TAG, "Added emergency contact: " + contact);
    }
    
    /**
     * Remove an emergency contact
     */
    public void removeEmergencyContact(String contactNumber) {
        emergencyContacts.removeIf(contact -> contact.contains(contactNumber));
        Log.d(TAG, "Removed emergency contact: " + contactNumber);
    }
    
    /**
     * Get list of emergency contacts
     */
    public List<String> getEmergencyContacts() {
        return new ArrayList<>(emergencyContacts);
    }
    
    /**
     * Start trip sharing with contacts
     */
    public void startTripSharing(String tripId, List<String> contacts) {
        this.sharedTripId = tripId;
        this.tripSharingActive = true;
        
        Log.d(TAG, "Trip sharing started for trip: " + tripId);
        
        // In a real app, this would send real-time location updates to contacts
        Toast.makeText(this, "Trip sharing activated. " + contacts.size() + " contacts notified.", Toast.LENGTH_LONG).show();
    }
    
    /**
     * Stop trip sharing
     */
    public void stopTripSharing() {
        this.tripSharingActive = false;
        this.sharedTripId = null;
        
        Log.d(TAG, "Trip sharing stopped");
        Toast.makeText(this, "Trip sharing deactivated.", Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Check if trip sharing is active
     */
    public boolean isTripSharingActive() {
        return tripSharingActive;
    }
    
    /**
     * Alert emergency contacts with message
     */
    private void alertEmergencyContacts(String message) {
        for (String contact : emergencyContacts) {
            Log.d(TAG, "Alerting emergency contact: " + contact + " with message: " + message);
            // In a real app, this would send SMS, call, or push notification to contact
        }
    }
    
    /**
     * Get current location (mock implementation)
     */
    private String getCurrentLocation() {
        // In a real app, this would get actual GPS coordinates
        return "-33.9249, 18.4241"; // Cape Town coordinates as example
    }
    
    /**
     * Verify trip safety (check if user is in safe area)
     */
    public boolean verifyTripSafety(String location) {
        // In a real app, this would check against known safe areas
        // For now, just return true
        return true;
    }
    
    /**
     * Report incident during trip
     */
    public void reportIncident(String tripId, String incidentType, String description) {
        Log.e(TAG, "INCIDENT REPORTED - Trip: " + tripId + ", Type: " + incidentType + ", Desc: " + description);
        
        // In a real app, this would send to admin panel and possibly authorities
        Toast.makeText(this, "Incident reported. Authorities notified.", Toast.LENGTH_LONG).show();
    }
    
    /**
     * Get trip safety score
     */
    public int getTripSafetyScore(String origin, String destination) {
        // Calculate safety score based on route (1-10 scale)
        // Higher score means safer
        return 8; // Mock value
    }
}