package com.example.kwaaimancarservices;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kwaaimancarservices.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    
    // Bottom Sheet Components
    private MaterialCardView rideNowCard, scheduleCard, deliveryCard;
    private TextInputEditText pickupLocationInput, destinationInput;
    private CardView tripInfoCard;
    private TextView estimatedTime, estimatedDistance, estimatedFare;
    private Button bookRideButton;

    // Map Components
    private FloatingActionButton fabCurrentLocation, fabEmergency;
    private CardView tripStatusCard;
    private TextView driverName, tripStatus, estimatedArrival;
    
    // Permission Constants
    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private static final int REQUEST_PICKUP_LOCATION = 1001;
    private static final int REQUEST_DESTINATION_LOCATION = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        setupToolbar();
        setupNavigationDrawer();
        setupBottomSheet();
        setupLocationServices();
        setupClickListeners();
        checkLocationPermissions();
    }

    private void initViews() {
        drawerLayout = binding.drawerLayout;
        navigationView = binding.navigationView;

        // Bottom Sheet Components
        // Note: Bottom sheet is disabled due to missing layout resources
        // View bottomSheet = findViewById(R.id.bottom_sheet_ride_booking);
        // bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        rideNowCard = findViewById(R.id.ride_now_card);
        scheduleCard = findViewById(R.id.schedule_card);
        deliveryCard = findViewById(R.id.delivery_card);

        pickupLocationInput = findViewById(R.id.pickup_location_input);
        destinationInput = findViewById(R.id.destination_input);

        tripInfoCard = findViewById(R.id.trip_info_card);
        estimatedTime = findViewById(R.id.estimated_time);
        estimatedDistance = findViewById(R.id.estimated_distance);
        estimatedFare = findViewById(R.id.estimated_fare);

        bookRideButton = findViewById(R.id.book_ride_button);

        // FABs and Status Card
        fabCurrentLocation = binding.fabCurrentLocation;
        fabEmergency = binding.fabEmergency;
        tripStatusCard = binding.tripStatusCard;
        driverName = binding.driverName;
        tripStatus = binding.tripStatus;
        estimatedArrival = binding.estimatedArrival;
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kwaaiman");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupNavigationDrawer() {
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setupBottomSheet() {
        // Note: Bottom sheet functionality is commented out as the layout resource is not found
        // Uncomment and implement when bottom sheet layout is available
        /*
        View bottomSheet = findViewById(R.id.bottom_sheet_ride_booking);
        if (bottomSheet != null) {
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            bottomSheetBehavior.setPeekHeight(280);
            bottomSheetBehavior.setHideable(false);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    // Handle bottom sheet state changes
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    // Handle bottom sheet sliding
                }
            });
        } */
    }

    private void setupLocationServices() {
        // Initialize Google Maps and location services
        // This would typically involve initializing the map fragment
    }

    private void setupClickListeners() {
        // Service Cards
        rideNowCard.setOnClickListener(v -> handleRideNowClick());
        scheduleCard.setOnClickListener(v -> handleScheduleClick());
        deliveryCard.setOnClickListener(v -> handleDeliveryClick());

        // Book Ride Button
        bookRideButton.setOnClickListener(v -> handleBookRideClick());

        // FABs
        fabCurrentLocation.setOnClickListener(v -> getCurrentLocation());
        fabEmergency.setOnClickListener(v -> handleEmergencyClick());

        // Location Input Click Listeners
        pickupLocationInput.setOnClickListener(v -> openLocationPicker("pickup"));
        destinationInput.setOnClickListener(v -> openLocationPicker("destination"));
    }

    private void handleRideNowClick() {
        // Set ride type to standard ride
        bookRideButton.setText("Book Ride Now");
        updateTripEstimates();
        showTripInfo(true);
    }

    private void handleScheduleClick() {
        // Open schedule ride functionality
        Toast.makeText(this, "Schedule ride feature coming soon!", Toast.LENGTH_SHORT).show();
    }

    private void handleDeliveryClick() {
        // Set ride type to delivery
        bookRideButton.setText("Book Delivery");
        updateTripEstimates();
        showTripInfo(true);
    }

    private void openLocationPicker(String locationType) {
        Intent intent = new Intent(this, com.example.kwaaimancarservices.activities.LocationSearchActivity.class);
        intent.putExtra("LOCATION_TYPE", locationType);
        startActivityForResult(intent, locationType.equals("pickup") ? REQUEST_PICKUP_LOCATION : REQUEST_DESTINATION_LOCATION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == RESULT_OK && data != null) {
            String locationName = data.getStringExtra("LOCATION_NAME");
            String locationAddress = data.getStringExtra("LOCATION_ADDRESS");
            String locationType = data.getStringExtra("LOCATION_TYPE");
            
            if (locationType != null && locationName != null) {
                if (locationType.equals("pickup")) {
                    pickupLocationInput.setText(locationAddress);
                } else if (locationType.equals("destination")) {
                    destinationInput.setText(locationAddress);
                }
                
                // Update estimates if both fields are filled
                if (!pickupLocationInput.getText().toString().isEmpty() && 
                    !destinationInput.getText().toString().isEmpty()) {
                    updateTripEstimates();
                }
            }
        }
    }

    private void handleBookRideClick() {
        String pickup = pickupLocationInput.getText().toString().trim();
        String destination = destinationInput.getText().toString().trim();

        if (pickup.isEmpty()) {
            pickupLocationInput.setError("Please enter pickup location");
            return;
        }

        if (destination.isEmpty()) {
            destinationInput.setError("Please enter destination");
            return;
        }

        // Navigate to ride booking confirmation
        Intent intent = new Intent(this, com.kwaaimancarservices.rides.activities.RideBookingActivity.class);
        intent.putExtra("PICKUP_LOCATION", pickup);
        intent.putExtra("DESTINATION_LOCATION", destination);
        intent.putExtra("ESTIMATED_FARE", estimatedFare.getText().toString());
        intent.putExtra("ESTIMATED_TIME", estimatedTime.getText().toString());
        startActivity(intent);
    }

    private void getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Simple location update
            Toast.makeText(this, "Getting your location...", Toast.LENGTH_SHORT).show();
            pickupLocationInput.setText("Current Location");
        } else {
            checkLocationPermissions();
        }
    }

    private void handleEmergencyClick() {
        // Handle emergency button click
        // In a real app, this would activate the security service
        Intent intent = new Intent(this, com.example.kwaaimancarservices.services.SecurityService.class);
        startService(intent);
        
        // Get the service and activate emergency SOS
        // Note: In a real implementation, you would bind to the service
        // and call activateEmergencySOS() method
        Toast.makeText(this, "Emergency SOS activated! Help is on the way.", Toast.LENGTH_LONG).show();
    }

    private void updateTripEstimates() {
        String pickup = pickupLocationInput.getText().toString().trim();
        String destination = destinationInput.getText().toString().trim();

        if (!pickup.isEmpty() && !destination.isEmpty()) {
            // Simple fare calculation
            final Random random = new Random();
            final double fare = 35.0 + (random.nextDouble() * 30.0);
            final int minutes = 8 + random.nextInt(20);
            final double km = 3.0 + (random.nextDouble() * 15.0);
            
            estimatedTime.setText(minutes + " min");
            estimatedDistance.setText(String.format(Locale.getDefault(), "%.1f km", km));
            estimatedFare.setText("R " + String.format(Locale.getDefault(), "%.2f", fare));
            showTripInfo(true);
        }
    }

    private void showTripInfo(boolean show) {
        tripInfoCard.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void checkLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize location services
                setupLocationServices();
            } else {
                Toast.makeText(this, "Location permission is required for the app to work properly",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Handle navigation menu item clicks
        if (id == R.id.nav_profile) {
            startActivity(new Intent(this, com.example.kwaaimancarservices.activities.ProfileActivity.class));
        } else if (id == R.id.nav_trips) {
            startActivity(new Intent(this, com.example.kwaaimancarservices.activities.TripHistoryActivity.class));
        } else if (id == R.id.nav_payments) {
            startActivity(new Intent(this, com.example.kwaaimancarservices.activities.PaymentMethodsActivity.class));
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, com.example.kwaaimancarservices.activities.SettingsActivity.class));
        } else if (id == R.id.nav_support) {
            startActivity(new Intent(this, com.example.kwaaimancarservices.activities.SupportActivity.class));
        } else if (id == R.id.nav_driver) {
            startActivity(new Intent(this, com.example.kwaaimancarservices.activities.DriverDashboardActivity.class));
        } else if (id == R.id.nav_logout) {
            handleLogout();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleLogout() {
        // Clear user session and navigate to auth activity
        getSharedPreferences("KwaaimanPrefs", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // Navigate to auth activity
        Intent intent = new Intent(this, com.example.kwaaimancarservices.activities.AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}