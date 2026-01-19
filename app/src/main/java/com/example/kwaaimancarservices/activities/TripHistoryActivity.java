package com.example.kwaaimancarservices.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kwaaimancarservices.R;
import com.example.kwaaimancarservices.adapters.TripHistoryAdapter;
import com.example.kwaaimancarservices.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class TripHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTrips;
    private TripHistoryAdapter adapter;
    private List<Trip> tripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_history);

        initViews();
        setupToolbar();
        loadTripHistory();
        setupRecyclerView();
    }

    private void initViews() {
        recyclerViewTrips = findViewById(R.id.recyclerViewTrips);
        tripList = new ArrayList<>();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Trip History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void loadTripHistory() {
        // Sample trip data
        tripList.add(new Trip("March 15, 2023", "R120.50", "123 Main Street, Cape Town", 
                "456 Oak Avenue, Cape Town", "12.5 km", "25 min"));
        tripList.add(new Trip("March 12, 2023", "R85.00", "789 Pine Road, Cape Town", 
                "321 Elm Street, Cape Town", "8.2 km", "18 min"));
        tripList.add(new Trip("March 10, 2023", "R150.75", "555 Market St, Cape Town", 
                "999 Beach Blvd, Cape Town", "15.3 km", "32 min"));
        tripList.add(new Trip("March 8, 2023", "R95.25", "111 Station Rd, Cape Town", 
                "222 Airport Way, Cape Town", "10.7 km", "22 min"));
        tripList.add(new Trip("March 5, 2023", "R110.00", "444 University Ave, Cape Town", 
                "666 Shopping Mall, Cape Town", "11.8 km", "24 min"));
    }

    private void setupRecyclerView() {
        adapter = new TripHistoryAdapter(tripList);
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrips.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}