package com.example.kwaaimancarservices.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kwaaimancarservices.R;
import com.example.kwaaimancarservices.services.PaymentService;
import com.example.kwaaimancarservices.services.DriverMatchingService;
import com.example.kwaaimancarservices.models.Location;
import com.google.android.material.card.MaterialCardView;

public class RideBookingActivity extends AppCompatActivity {

    private TextView pickupLocationText, destinationLocationText, estimatedFareText, estimatedTimeText;
    private TextView driverNameText, driverPhoneText, carDetailsText;
    private MaterialCardView driverInfoCard;
    private ProgressBar findingDriverProgress;
    private Button cancelButton;
    private String pickupLocation, destinationLocation, estimatedFare, estimatedTime;
    private PaymentService paymentService;
    private DriverMatchingService matchingService;
    private DriverMatchingService.DriverMatchingBinder serviceBinder;
    private boolean serviceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_booking);

        // Get booking details from intent
        pickupLocation = getIntent().getStringExtra("PICKUP_LOCATION");
        destinationLocation = getIntent().getStringExtra("DESTINATION_LOCATION");
        estimatedFare = getIntent().getStringExtra("ESTIMATED_FARE");
        estimatedTime = getIntent().getStringExtra("ESTIMATED_TIME");

        initViews();
        setupToolbar();
        initializePaymentService();
        populateBookingDetails();
        simulateDriverFinding();
        setupClickListeners();
    }
        
    private void initializePaymentService() {
        paymentService = new PaymentService(this);
    }
        
    private void initViews() {
        pickupLocationText = findViewById(R.id.pickup_location_text);
        destinationLocationText = findViewById(R.id.destination_location_text);
        estimatedFareText = findViewById(R.id.estimated_fare_text);
        estimatedTimeText = findViewById(R.id.estimated_time_text);
        driverNameText = findViewById(R.id.driver_name_text);
        driverPhoneText = findViewById(R.id.driver_phone_text);
        carDetailsText = findViewById(R.id.car_details_text);
        driverInfoCard = findViewById(R.id.driver_info_card);
        findingDriverProgress = findViewById(R.id.finding_driver_progress);
        cancelButton = findViewById(R.id.cancel_button);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Booking Confirmation");
        }
    }

    private void populateBookingDetails() {
        if (pickupLocation != null) pickupLocationText.setText(pickupLocation);
        if (destinationLocation != null) destinationLocationText.setText(destinationLocation);
        if (estimatedFare != null) estimatedFareText.setText(estimatedFare);
        if (estimatedTime != null) estimatedTimeText.setText(estimatedTime);
    }

    private void simulateDriverFinding() {
        // Initially hide driver info
        driverInfoCard.setVisibility(View.GONE);
        findingDriverProgress.setVisibility(View.VISIBLE);
        findingDriverProgress.setIndeterminate(true);

        // Find a driver using the matching service
        if (pickupLocation != null && destinationLocation != null) {
            // Create temporary location objects (in a real app, these would come with the intent)
            // For demo purposes, we'll use generic coordinates
            com.example.kwaaimancarservices.models.Location pickupLoc = 
                new com.example.kwaaimancarservices.models.Location("Pickup", pickupLocation, -33.9249, 18.4241);
            com.example.kwaaimancarservices.models.Location destLoc = 
                new com.example.kwaaimancarservices.models.Location("Destination", destinationLocation, -33.9049, 18.4190);
                
            // Find driver for passenger
            String requestId = "REQ_" + System.currentTimeMillis(); // Generate unique request ID
            
            // Initialize the matching service
            Intent intent = new Intent(this, DriverMatchingService.class);
            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }
    
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceBinder = (DriverMatchingService.DriverMatchingBinder) service;
            matchingService = serviceBinder.getService();
            serviceBound = true;
            
            // Set callback and find driver
            matchingService.setCallback(new DriverMatchingService.MatchingCallback() {
                @Override
                public void onDriverFound(DriverMatchingService.Driver driver) {
                    runOnUiThread(() -> {
                        findingDriverProgress.setVisibility(View.GONE);
                        driverInfoCard.setVisibility(View.VISIBLE);
                        
                        // Display driver information
                        driverNameText.setText(driver.getName());
                        driverPhoneText.setText(driver.getPhoneNumber());
                        carDetailsText.setText(driver.getVehicleDetails());
                        
                        Toast.makeText(RideBookingActivity.this, 
                            "Driver found! " + driver.getName() + " is on the way to your location.", 
                            Toast.LENGTH_LONG).show();
                        
                        // Navigate to trip tracking activity
                        Intent trackingIntent = new Intent(RideBookingActivity.this, com.example.kwaaimancarservices.activities.TripTrackingActivity.class);
                        trackingIntent.putExtra("DRIVER_NAME", driver.getName());
                        trackingIntent.putExtra("DRIVER_PHONE", driver.getPhoneNumber());
                        trackingIntent.putExtra("CAR_DETAILS", driver.getVehicleDetails());
                        startActivity(trackingIntent);
                        finish(); // Close this activity
                    });
                }

                @Override
                public void onDriverNotFound() {
                    runOnUiThread(() -> {
                        findingDriverProgress.setVisibility(View.GONE);
                        Toast.makeText(RideBookingActivity.this, 
                            "No drivers available in your area. Please try again later.", 
                            Toast.LENGTH_LONG).show();
                    });
                }

                @Override
                public void onMatchInProgress() {
                    runOnUiThread(() -> {
                        Toast.makeText(RideBookingActivity.this, 
                            "Searching for available drivers...", 
                            Toast.LENGTH_SHORT).show();
                    });
                }
            });
            
            // Actually find a driver
            com.example.kwaaimancarservices.models.Location pickupLoc = 
                new com.example.kwaaimancarservices.models.Location("Pickup", pickupLocation, -33.9249, 18.4241);
            matchingService.findDriverForPassenger("REQ_" + System.currentTimeMillis(), pickupLoc, null);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };

    private void setupClickListeners() {
        cancelButton.setOnClickListener(v -> {
            // Cancel the booking
            Toast.makeText(this, "Booking cancelled", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unbind from the service to prevent memory leaks
        if (serviceBound) {
            unbindService(serviceConnection);
            serviceBound = false;
        }
    }
}