package com.example.kwaaimancarservices.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kwaaimancarservices.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profileImageView;
    private TextView nameTextView, emailTextView, phoneTextView, ratingTextView;
    private CardView editProfileCard, emergencyContactCard, verificationCard;
    private Button becomeDriverButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        preferences = getSharedPreferences("KwaaimanPrefs", MODE_PRIVATE);

        initViews();
        setupToolbar();
        setupClickListeners();
        loadUserData();
    }

    private void initViews() {
        profileImageView = findViewById(R.id.profile_image_view);
        nameTextView = findViewById(R.id.name_text_view);
        emailTextView = findViewById(R.id.email_text_view);
        phoneTextView = findViewById(R.id.phone_text_view);
        ratingTextView = findViewById(R.id.rating_text_view);
        editProfileCard = findViewById(R.id.edit_profile_card);
        emergencyContactCard = findViewById(R.id.emergency_contact_card);
        verificationCard = findViewById(R.id.verification_card);
        becomeDriverButton = findViewById(R.id.become_driver_button);
    }

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }
    }

    private void setupClickListeners() {
        editProfileCard.setOnClickListener(v -> {
            // Open edit profile activity
            Toast.makeText(this, "Edit Profile - Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        emergencyContactCard.setOnClickListener(v -> {
            // Open emergency contact settings
            Toast.makeText(this, "Emergency Contacts - Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        verificationCard.setOnClickListener(v -> {
            // Open verification activity
            Toast.makeText(this, "Account Verification - Coming Soon!", Toast.LENGTH_SHORT).show();
        });

        becomeDriverButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, DriverRegistrationActivity.class);
            startActivity(intent);
        });
    }

    private void loadUserData() {
        // Load user data from preferences or database
        String userName = preferences.getString("user_name", "John Doe");
        String userEmail = preferences.getString("user_email", "john.doe@example.com");
        String userPhone = preferences.getString("user_phone", "+27 12 345 6789");
        float userRating = preferences.getFloat("user_rating", 4.8f);

        nameTextView.setText(userName);
        emailTextView.setText(userEmail);
        phoneTextView.setText(userPhone);
        ratingTextView.setText(String.format("%.1f", userRating));

        // Set profile image (you could load from URL or local storage)
        profileImageView.setImageResource(R.drawable.default_profile_image);
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