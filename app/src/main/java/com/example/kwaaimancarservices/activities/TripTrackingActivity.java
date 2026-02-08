package com.example.kwaaimancarservices.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.kwaaimancarservices.R;

import java.util.ArrayList;
import java.util.List;

public class TripTrackingActivity extends AppCompatActivity implements OnMapReadyCallback {
    
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    
    private TextView driverNameText, driverPhoneText, carDetailsText, tripStatusText, etaText;
    private Button cancelButton, contactDriverButton;
    
    private String driverName, driverPhone, carDetails;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    
    private Polyline currentPolyline;
    private List<LatLng> routePoints;
    
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_tracking);
        
        // Get driver details from intent
        driverName = getIntent().getStringExtra("DRIVER_NAME");
        driverPhone = getIntent().getStringExtra("DRIVER_PHONE");
        carDetails = getIntent().getStringExtra("CAR_DETAILS");
        
        initViews();
        setupMap();
        setupLocationTracking();
        populateDriverInfo();
        setupClickListeners();
    }
    
    private void initViews() {
        driverNameText = findViewById(R.id.driver_name_text);
        driverPhoneText = findViewById(R.id.driver_phone_text);
        carDetailsText = findViewById(R.id.car_details_text);
        tripStatusText = findViewById(R.id.trip_status_text);
        etaText = findViewById(R.id.eta_text);
        cancelButton = findViewById(R.id.cancel_button);
        contactDriverButton = findViewById(R.id.contact_driver_button);
    }
    
    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }
    
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }
    
    private void setupLocationTracking() {
        // Create location request
        locationRequest = LocationRequest.create()
                .setInterval(5000) // Update every 5 seconds
                .setFastestInterval(2000) // Fastest interval
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                
                for (Location location : locationResult.getLocations()) {
                    updateMyLocation(location);
                }
            }
        };
        
        startLocationUpdates();
    }
    
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        } else {
            requestLocationPermission();
        }
    }
    
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
    
    private void updateMyLocation(Location location) {
        if (mMap != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            
            // Move camera to current location
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            
            // Add or update user marker
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Your Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            
            // Add driver marker (simulated)
            addDriverMarker();
            
            // Add route polyline (simulated)
            addRoutePolyline(latLng);
        }
    }
    
    private void addDriverMarker() {
        // In a real app, this would come from the driver's real-time location
        // For demo, we'll simulate a driver moving along a route
        LatLng driverPosition = new LatLng(-33.9250, 18.4242); // Near user position
        mMap.addMarker(new MarkerOptions()
                .position(driverPosition)
                .title("Your Driver")
                .snippet(driverName)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
    }
    
    private void addRoutePolyline(LatLng userLocation) {
        if (routePoints == null) {
            routePoints = new ArrayList<>();
            routePoints.add(userLocation);
        } else {
            routePoints.add(userLocation);
        }
        
        if (currentPolyline != null) {
            currentPolyline.remove();
        }
        
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(routePoints)
                .color(Color.BLUE)
                .width(10);
        
        currentPolyline = mMap.addPolyline(polylineOptions);
    }
    
    private void populateDriverInfo() {
        if (driverName != null) driverNameText.setText(driverName);
        if (driverPhone != null) driverPhoneText.setText(driverPhone);
        if (carDetails != null) carDetailsText.setText(carDetails);
        
        tripStatusText.setText("In Progress");
        etaText.setText("Arriving in 3 mins");
    }
    
    private void setupClickListeners() {
        cancelButton.setOnClickListener(v -> {
            // Cancel the trip
            Toast.makeText(this, "Trip cancelled", Toast.LENGTH_SHORT).show();
            finish();
        });
        
        contactDriverButton.setOnClickListener(v -> {
            // In a real app, this would initiate a call or chat with the driver
            Toast.makeText(this, "Calling " + driverName + "...", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE);
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission is required for trip tracking", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (mMap != null) {
            startLocationUpdates();
        }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }
}