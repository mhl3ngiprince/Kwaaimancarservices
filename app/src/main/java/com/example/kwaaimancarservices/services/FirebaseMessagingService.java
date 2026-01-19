package com.example.kwaaimancarservices.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.kwaaimancarservices.MainActivity;
import com.example.kwaaimancarservices.R;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String CHANNEL_ID = "kwaaiman_notifications";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Handle data payload of FCM messages
        if (remoteMessage.getData().size() > 0) {
            handleDataMessage(remoteMessage);
        }

        // Handle notification payload
        if (remoteMessage.getNotification() != null) {
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody()
            );
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        // Send token to server or save locally
        sendTokenToServer(token);
    }

    private void handleDataMessage(RemoteMessage remoteMessage) {
        String messageType = remoteMessage.getData().get("type");

        if ("ride_request".equals(messageType)) {
            // Handle ride request notification
            showNotification("New Ride Request", "You have a new ride request nearby");
        } else if ("driver_arrived".equals(messageType)) {
            // Handle driver arrival notification
            showNotification("Driver Arrived", "Your driver has arrived at pickup location");
        } else if ("trip_completed".equals(messageType)) {
            // Handle trip completion notification
            showNotification("Trip Completed", "Your trip has been completed");
        }
    }

    private void showNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Kwaaiman Notifications";
            String description = "Notifications for ride updates and requests";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendTokenToServer(String token) {
        // Send token to your server for push notifications
        // This would typically involve making an API call
    }
}