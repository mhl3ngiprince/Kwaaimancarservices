package com.example.kwaaimancarservices.activities;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kwaaimancarservices.MainActivity;
import com.example.kwaaimancarservices.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView logoImage;
    private TextView appName;
    private TextView tagline;
    private ProgressBar loadingProgress;
    private Handler splashHandler;
    private static final int SPLASH_DURATION = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initViews();
        startAnimations();
        handleSplashDelay();
    }

    private void initViews() {
        logoImage = findViewById(R.id.logo_image);
        appName = findViewById(R.id.app_name);
        tagline = findViewById(R.id.tagline);
        loadingProgress = findViewById(R.id.loading_progress);
    }

    private void startAnimations() {
        // Logo scale animation
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(logoImage, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(logoImage, "scaleY", 0f, 1f);
        scaleX.setDuration(1000);
        scaleY.setDuration(1000);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleX.start();
        scaleY.start();

        // App name fade in
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f);
            fadeIn.setDuration(800);
            fadeIn.start();
            appName.setVisibility(View.VISIBLE);
        }, 500);

        // Tagline slide up
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            ObjectAnimator slideUp = ObjectAnimator.ofFloat(tagline, "translationY", 100f, 0f);
            ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f);
            slideUp.setDuration(600);
            fadeIn.setDuration(600);
            slideUp.start();
            fadeIn.start();
            tagline.setVisibility(View.VISIBLE);
        }, 1000);

        // Progress bar animation
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingProgress.setVisibility(View.VISIBLE);
            ObjectAnimator progressAnim = ObjectAnimator.ofFloat(loadingProgress, "alpha", 0f, 1f);
            progressAnim.setDuration(400);
            progressAnim.start();
        }, 1500);
    }

    private void handleSplashDelay() {
        splashHandler = new Handler(Looper.getMainLooper());
        splashHandler.postDelayed(() -> {
            checkUserStatus();
        }, SPLASH_DURATION);
    }

    private void checkUserStatus() {
        SharedPreferences preferences = getSharedPreferences("KwaaimanPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("first_launch", true);
        boolean isLoggedIn = preferences.getBoolean("is_logged_in", false);

        Intent intent;
        if (isFirstLaunch) {
            intent = new Intent(this, OnboardingActivity.class);
        } else if (!isLoggedIn) {
            intent = new Intent(this, AuthActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (splashHandler != null) {
            splashHandler.removeCallbacksAndMessages(null);
        }
    }
}