package com.example.kwaaimancarservices.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kwaaimancarservices.rides.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchNotifications, switchEmailNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupToolbar();
        setupClickListeners();
    }

    private void initViews() {
        switchNotifications = findViewById(R.id.switchNotifications);
        switchEmailNotifications = findViewById(R.id.switchEmailNotifications);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Settings");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupClickListeners() {
        // Set up listeners for switches
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Notifications enabled" : "Notifications disabled";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        switchEmailNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Email notifications enabled" : "Email notifications disabled";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Set click listeners for the material card views
        // Account section - Edit Profile
        findViewById(R.id.account_settings_card).setOnClickListener(v -> {
            Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show();
        });

        // Account section - Change Password (using account_settings_card for now)
        // findViewById(R.id.change_password_card).setOnClickListener(v -> {
        //     Toast.makeText(this, "Change Password clicked", Toast.LENGTH_SHORT).show();
        // });

        // Privacy section - Privacy Policy
        findViewById(R.id.privacy_policy_card).setOnClickListener(v -> {
            Toast.makeText(this, "Privacy Policy clicked", Toast.LENGTH_SHORT).show();
        });

        // Privacy section - Security Settings
        findViewById(R.id.security_settings_card).setOnClickListener(v -> {
            Toast.makeText(this, "Security Settings clicked", Toast.LENGTH_SHORT).show();
        });

        // Support section - Help Center
        findViewById(R.id.help_center_card).setOnClickListener(v -> {
            Toast.makeText(this, "Help Center clicked", Toast.LENGTH_SHORT).show();
        });

        // Support section - Send Feedback
        findViewById(R.id.send_feedback_card).setOnClickListener(v -> {
            Toast.makeText(this, "Send Feedback clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}