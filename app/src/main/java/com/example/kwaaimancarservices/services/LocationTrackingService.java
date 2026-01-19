package com.example.kwaaimancarservices.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.kwaaimancarservices.R;

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

    private void startLocationTracking() {
        // Implement location tracking logic
        // This would typically use LocationManager or FusedLocationProviderClient
    }

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