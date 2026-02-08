package com.example.kwaaimancarservices.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationAvailability;

import com.kwaaimancarservices.rides.R;

public class LocationTrackingService extends Service {

    private static final int SERVICE_ID = 1001;
    private static final String CHANNEL_ID = "location_tracking";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(SERVICE_ID, createNotification());

        // Start location tracking logic here
        startLocationTracking();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    
    private void startLocationTracking() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        
        locationRequest = LocationRequest.create()
                .setInterval(10000) // Update every 10 seconds
                .setFastestInterval(5000) // Fastest interval
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    // Handle location updates
                    handleLocationUpdate(location);
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                // Handle location availability
                if (!locationAvailability.isLocationAvailable()) {
                    Log.w(TAG, "Location is not available");
                }
            }
        };
        
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }
    
    private void handleLocationUpdate(Location location) {
        // In a real app, this would send location to server
        Log.d(TAG, "Location updated: " + location.getLatitude() + ", " + location.getLongitude());
        
        // You could send this to Firebase or your backend
        // FirebaseHelper.getInstance().updateUserLocation(location);
    }
    
    private void stopLocationTracking() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        stopLocationTracking();
    }
    
    private static final String TAG = "LocationTrackingService";

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Kwaaiman")
                .setContentText("Tracking your location for trip updates")
                .setSmallIcon(R.drawable.ic_location_on)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Tracking",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}