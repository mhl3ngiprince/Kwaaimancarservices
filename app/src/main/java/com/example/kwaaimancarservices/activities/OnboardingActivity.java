package com.example.kwaaimancarservices.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.kwaaimancarservices.rides.R;
import com.kwaaimancarservices.rides.adapters.OnboardingAdapter;
import com.kwaaimancarservices.rides.models.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private LinearLayout indicatorsLayout;
    private Button nextButton;
    private Button skipButton;
    private TextView getStartedButton;
    private OnboardingAdapter adapter;
    private List<OnboardingItem> onboardingItems;
    private int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        initViews();
        setupOnboardingItems();
        setupViewPager();
        setupIndicators();
        setupButtons();
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPager);
        indicatorsLayout = findViewById(R.id.indicators_layout);
        nextButton = findViewById(R.id.next_button);
        skipButton = findViewById(R.id.skip_button);
        getStartedButton = findViewById(R.id.get_started_button);
    }

    private void setupOnboardingItems() {
        onboardingItems = new ArrayList<>();

        onboardingItems.add(new OnboardingItem(
                R.drawable.onboarding_1,
                "Quick & Safe Rides",
                "Get reliable transportation to and from campus with verified student drivers"
        ));

        onboardingItems.add(new OnboardingItem(
                R.drawable.onboarding_2,
                "Student-Friendly Pricing",
                "Affordable rates designed specifically for students in Kimberley"
        ));

        onboardingItems.add(new OnboardingItem(
                R.drawable.onboarding_3,
                "Real-Time Tracking",
                "Track your ride in real-time and share your location with friends for safety"
        ));

        onboardingItems.add(new OnboardingItem(
                R.drawable.onboarding_4,
                "Schedule in Advance",
                "Book rides for classes, events, or weekend trips ahead of time"
        ));
    }

    private void setupViewPager() {
        adapter = new OnboardingAdapter(onboardingItems);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                updateIndicators(position);
                updateButtons(position);
            }
        });
    }

    private void setupIndicators() {
        ImageView[] indicators = new ImageView[onboardingItems.size()];

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageResource(R.drawable.indicator_inactive);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);

            indicatorsLayout.addView(indicators[i], params);
        }

        // Set first indicator as active
        if (indicators.length > 0) {
            indicators[0].setImageResource(R.drawable.indicator_active);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicatorsLayout.getChildCount(); i++) {
            ImageView indicator = (ImageView) indicatorsLayout.getChildAt(i);
            if (i == position) {
                indicator.setImageResource(R.drawable.indicator_active);
            } else {
                indicator.setImageResource(R.drawable.indicator_inactive);
            }
        }
    }

    private void updateButtons(int position) {
        if (position == onboardingItems.size() - 1) {
            nextButton.setVisibility(View.GONE);
            skipButton.setVisibility(View.GONE);
            getStartedButton.setVisibility(View.VISIBLE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
            skipButton.setVisibility(View.VISIBLE);
            getStartedButton.setVisibility(View.GONE);
        }
    }

    private void setupButtons() {
        nextButton.setOnClickListener(v -> {
            if (currentPosition < onboardingItems.size() - 1) {
                viewPager.setCurrentItem(currentPosition + 1);
            }
        });

        skipButton.setOnClickListener(v -> finishOnboarding());
        getStartedButton.setOnClickListener(v -> finishOnboarding());
    }

    private void finishOnboarding() {
        // Save that onboarding has been completed
        SharedPreferences preferences = getSharedPreferences("KwaaimanPrefs", MODE_PRIVATE);
        preferences.edit().putBoolean("first_launch", false).apply();

        // Navigate to authentication
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}