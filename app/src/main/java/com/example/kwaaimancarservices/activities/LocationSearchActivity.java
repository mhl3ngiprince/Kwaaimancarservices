package com.example.kwaaimancarservices.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kwaaimancarservices.R;
import com.example.kwaaimancarservices.adapters.LocationSearchAdapter;
import com.example.kwaaimancarservices.databinding.ActivityLocationSearchBinding;
import com.example.kwaaimancarservices.models.Location;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class LocationSearchActivity extends AppCompatActivity implements LocationSearchAdapter.OnLocationClickListener {

    private ActivityLocationSearchBinding binding;
    private LocationSearchAdapter adapter;
    private List<Location> locations;
    private String locationType; // "pickup" or "destination"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get location type from intent
        locationType = getIntent().getStringExtra("LOCATION_TYPE");

        initViews();
        setupToolbar();
        setupRecyclerView();
        setupSearchListener();
        loadSampleLocations();
    }

    private void initViews() {
        // Views are already initialized via View Binding
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Select Location");
        }
    }

    private void setupRecyclerView() {
        locations = new ArrayList<>();
        adapter = new LocationSearchAdapter(this, this);
        binding.locationsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.locationsRecyclerView.setAdapter(adapter);
    }

    private void setupSearchListener() {
        binding.searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterLocations(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                filterLocations(binding.searchEditText.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void loadSampleLocations() {
        // Add sample locations for testing
        locations.add(new Location("Current Location", "Your current location"));
        locations.add(new Location("University of Cape Town", "Private Bag X3, Rondebosch, Cape Town, 7701"));
        locations.add(new Location("Cape Town City Center", "Adderley Street, Cape Town City Center"));
        locations.add(new Location("V&A Waterfront", "Nelson Mandela Boulevard, Cape Town"));
        locations.add(new Location("Cape Town International Airport", "Airport Road, Cape Town"));
        locations.add(new Location("Green Point Stadium", "Avianca Street, Green Point"));
        locations.add(new Location("Stellenbosch University", "Stellenbosch, Western Cape"));
        locations.add(new Location("Bellville CBD", "Church Street, Bellville, Cape Town"));

        adapter.updateLocations(locations);
    }

    private void filterLocations(String query) {
        List<Location> filteredList = new ArrayList<>();
        
        if (query.trim().isEmpty()) {
            // Show all locations if search is empty
            filteredList.addAll(locations);
        } else {
            // Filter locations based on query
            for (Location location : locations) {
                if (location.getName().toLowerCase().contains(query.toLowerCase()) ||
                    location.getAddress().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(location);
                }
            }
        }
        
        adapter.updateLocations(filteredList);
    }

    @Override
    public void onLocationClick(Location location) {
        // Return the selected location to the calling activity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("LOCATION_NAME", location.getName());
        resultIntent.putExtra("LOCATION_ADDRESS", location.getAddress());
        resultIntent.putExtra("LOCATION_TYPE", locationType);
        
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}