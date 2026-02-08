package com.example.kwaaimancarservices.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kwaaimancarservices.rides.R;

public class SupportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        setupToolbar();
        setupClickListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Support");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupClickListeners() {
        Button buttonCallSupport = findViewById(R.id.buttonCallSupport);
        Button buttonChatSupport = findViewById(R.id.buttonChatSupport);

        if (buttonCallSupport != null) {
            buttonCallSupport.setOnClickListener(v -> {
                // Call support functionality
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+27123456789")); // South African number format
                try {
                    startActivity(callIntent);
                } catch (SecurityException e) {
                    Toast.makeText(this, "Permission denied to make call", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (buttonChatSupport != null) {
            buttonChatSupport.setOnClickListener(v -> {
                // Live chat functionality
                Toast.makeText(this, "Connecting to live chat support...", Toast.LENGTH_SHORT).show();
            });
        }

        // Set click listeners for FAQ items (commented out due to missing layout IDs)
        // findViewById(R.id.how_to_cancel_ride).setOnClickListener(v -> {
        //     Toast.makeText(this, "To cancel a ride, go to your active trips and tap 'Cancel Ride'", Toast.LENGTH_LONG).show();
        // });
        //
        // findViewById(R.id.payment_options).setOnClickListener(v -> {
        //     Toast.makeText(this, "We accept credit cards, debit cards, and mobile money", Toast.LENGTH_LONG).show();
        // });
        //
        // findViewById(R.id.price_calculation).setOnClickListener(v -> {
        //     Toast.makeText(this, "Prices are calculated based on distance, time, and demand", Toast.LENGTH_LONG).show();
        // });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}