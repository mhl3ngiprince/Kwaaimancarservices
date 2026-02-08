package com.example.kwaaimancarservices.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kwaaimancarservices.rides.R;
import com.google.android.material.textfield.TextInputEditText;

public class DriverRegistrationActivity extends AppCompatActivity {

    private TextInputEditText licenseNumberInput, vehicleModelInput, vehicleColorInput, vehiclePlateInput;
    private Button submitApplicationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_registration);

        initViews();
        setupToolbar();
        setupClickListeners();
    }

    private void initViews() {
        licenseNumberInput = findViewById(R.id.license_number_input);
        vehicleModelInput = findViewById(R.id.vehicle_model_input);
        vehicleColorInput = findViewById(R.id.vehicle_color_input);
        vehiclePlateInput = findViewById(R.id.vehicle_plate_input);
        submitApplicationButton = findViewById(R.id.submit_application_button);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Become a Driver");
        }
    }

    private void setupClickListeners() {
        submitApplicationButton.setOnClickListener(v -> submitApplication());
    }

    private void submitApplication() {
        String licenseNumber = licenseNumberInput.getText().toString().trim();
        String vehicleModel = vehicleModelInput.getText().toString().trim();
        String vehicleColor = vehicleColorInput.getText().toString().trim();
        String vehiclePlate = vehiclePlateInput.getText().toString().trim();

        if (licenseNumber.isEmpty()) {
            licenseNumberInput.setError("License number required");
            return;
        }

        if (vehicleModel.isEmpty()) {
            vehicleModelInput.setError("Vehicle model required");
            return;
        }

        if (vehicleColor.isEmpty()) {
            vehicleColorInput.setError("Vehicle color required");
            return;
        }

        if (vehiclePlate.isEmpty()) {
            vehiclePlateInput.setError("Vehicle plate number required");
            return;
        }

        // Submit application
        Toast.makeText(this, "Application submitted! We'll review and get back to you.", Toast.LENGTH_LONG).show();
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