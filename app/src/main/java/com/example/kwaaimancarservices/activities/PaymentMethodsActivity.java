package com.example.kwaaimancarservices.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.kwaaimancarservices.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class PaymentMethodsActivity extends AppCompatActivity {

    private SwitchMaterial switchNotifications, switchEmailNotifications;

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
        Button buttonAddCard = findViewById(R.id.buttonAddCard);
        buttonAddCard.setOnClickListener(v -> {
            Toast.makeText(this, "Add new payment method", Toast.LENGTH_SHORT).show();
        });

        ImageButton buttonDeleteCard = findViewById(R.id.buttonDeleteCard);
        buttonDeleteCard.setOnClickListener(v -> {
            Toast.makeText(this, "Card removed", Toast.LENGTH_SHORT).show();
        });

        ImageButton buttonDeleteCard2 = findViewById(R.id.buttonDeleteCard2);
        buttonDeleteCard2.setOnClickListener(v -> {
            Toast.makeText(this, "Card removed", Toast.LENGTH_SHORT).show();
        });

        SwitchMaterial switchCash = findViewById(R.id.switchCash);
        switchCash.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = isChecked ? "Cash payment enabled" : "Cash payment disabled";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}