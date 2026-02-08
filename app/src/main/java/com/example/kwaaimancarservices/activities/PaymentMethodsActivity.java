package com.example.kwaaimancarservices.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.kwaaimancarservices.rides.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PaymentMethodsActivity extends AppCompatActivity {

    private SwitchMaterial switchNotifications, switchEmailNotifications, switchCash;
    private Button buttonAddCard;
    private ImageButton buttonDeleteCard, buttonDeleteCard2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        initViews();
        setupToolbar();
        setupClickListeners();
    }

    private void initViews() {
        switchNotifications = findViewById(R.id.switchNotifications);
        switchEmailNotifications = findViewById(R.id.switchEmailNotifications);
        switchCash = findViewById(R.id.switchCash);
        buttonAddCard = findViewById(R.id.buttonAddCard);
        buttonDeleteCard = findViewById(R.id.buttonDeleteCard);
        buttonDeleteCard2 = findViewById(R.id.buttonDeleteCard2);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Payment Methods");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupClickListeners() {
        buttonAddCard.setOnClickListener(v -> {
            // Navigate to add payment method screen
            Toast.makeText(this, "Adding new payment method", Toast.LENGTH_SHORT).show();
            // In a real app, this would open a form to add a new payment method
        });

        buttonDeleteCard.setOnClickListener(v -> {
            // Confirm deletion
            showDeleteConfirmationDialog(0); // Card index
        });

        buttonDeleteCard2.setOnClickListener(v -> {
            // Confirm deletion
            showDeleteConfirmationDialog(1); // Card index
        });

        switchCash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Cash payment enabled" : "Cash payment disabled";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            
            // Save the setting
            getSharedPreferences("KwaaimanPrefs", MODE_PRIVATE)
                .edit()
                .putBoolean("cash_payment_enabled", isChecked)
                .apply();
        });
    }
    
    private void showDeleteConfirmationDialog(int cardIndex) {
        // In a real app, show a proper confirmation dialog
        Toast.makeText(this, "Card removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}