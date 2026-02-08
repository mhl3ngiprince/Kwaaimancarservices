package com.example.kwaaimancarservices.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kwaaimancarservices.R;

public class DriverDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_dashboard);

        setupToolbar();
        initDashboardViews();
        loadDriverStats();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.driver_dashboard_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initDashboardViews() {
        // Initialize dashboard statistics views
        TextView todayEarnings = findViewById(R.id.today_earnings);
        TextView weeklyEarnings = findViewById(R.id.weekly_earnings);
        TextView monthlyEarnings = findViewById(R.id.monthly_earnings);
        TextView totalRides = findViewById(R.id.total_rides);
        TextView rating = findViewById(R.id.driver_rating);
        
        if (todayEarnings != null) todayEarnings.setText(getString(R.string.zero_currency));
        if (weeklyEarnings != null) weeklyEarnings.setText(getString(R.string.zero_currency));
        if (monthlyEarnings != null) monthlyEarnings.setText(getString(R.string.zero_currency));
        if (totalRides != null) totalRides.setText(getString(R.string.zero_value));
        if (rating != null) rating.setText(getString(R.string.zero_rating));
    }

    private void loadDriverStats() {
        // Simulate loading driver statistics
        Toast.makeText(this, "Loading driver statistics...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}