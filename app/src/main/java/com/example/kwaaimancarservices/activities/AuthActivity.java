package com.example.kwaaimancarservices.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kwaaimancarservices.rides.MainActivity;
import com.kwaaimancarservices.rides.R;

public class AuthActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button authButton;
    private TextView switchModeText;
    private ProgressBar loadingProgress;
    private TextView forgotPasswordText;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initViews();
        setupClickListeners();
        updateUI();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        authButton = findViewById(R.id.auth_button);
        switchModeText = findViewById(R.id.switch_mode_text);
        loadingProgress = findViewById(R.id.loading_progress);
        forgotPasswordText = findViewById(R.id.forgot_password_text);
    }

    private void setupClickListeners() {
        authButton.setOnClickListener(v -> handleAuthAction());
        switchModeText.setOnClickListener(v -> switchAuthMode());
        forgotPasswordText.setOnClickListener(v -> handleForgotPassword());
    }

    private void updateUI() {
        if (isLoginMode) {
            authButton.setText("Sign In");
            switchModeText.setText("Don't have an account? Sign Up");
            confirmPasswordEditText.setVisibility(View.GONE);
            forgotPasswordText.setVisibility(View.VISIBLE);
        } else {
            authButton.setText("Sign Up");
            switchModeText.setText("Already have an account? Sign In");
            confirmPasswordEditText.setVisibility(View.VISIBLE);
            forgotPasswordText.setVisibility(View.GONE);
        }
    }

    private void switchAuthMode() {
        isLoginMode = !isLoginMode;
        updateUI();
        clearFields();
    }

    private void clearFields() {
        emailEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
    }

    private void handleAuthAction() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (!validateInput(email, password, confirmPassword)) {
            return;
        }

        showLoading(true);

        if (isLoginMode) {
            performLogin(email, password);
        } else {
            performRegistration(email, password);
        }
    }

    private boolean validateInput(String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return false;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return false;
        }

        if (!isLoginMode) {
            if (TextUtils.isEmpty(confirmPassword)) {
                confirmPasswordEditText.setError("Please confirm your password");
                return false;
            }

            if (!password.equals(confirmPassword)) {
                confirmPasswordEditText.setError("Passwords do not match");
                return false;
            }
        }

        return true;
    }

    private void performLogin(String email, String password) {
        // Simulate login process
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate network call
                runOnUiThread(() -> {
                    showLoading(false);
                    // For demo purposes, accept any valid credentials
                    if (email.contains("@") && password.length() >= 6) {
                        loginSuccess();
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void performRegistration(String email, String password) {
        // Simulate registration process
        new Thread(() -> {
            try {
                Thread.sleep(2500); // Simulate network call
                runOnUiThread(() -> {
                    showLoading(false);
                    Toast.makeText(this, "Registration successful! Please sign in.", Toast.LENGTH_SHORT).show();
                    isLoginMode = true;
                    updateUI();
                    clearFields();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void loginSuccess() {
        // Save login state
        SharedPreferences preferences = getSharedPreferences("KwaaimanPrefs", MODE_PRIVATE);
        preferences.edit()
                .putBoolean("is_logged_in", true)
                .putString("user_email", emailEditText.getText().toString())
                .apply();

        // Navigate to main activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void handleForgotPassword() {
        String email = emailEditText.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter your email first");
            return;
        }

        Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_LONG).show();
    }

    private void showLoading(boolean show) {
        if (show) {
            loadingProgress.setVisibility(View.VISIBLE);
            authButton.setEnabled(false);
        } else {
            loadingProgress.setVisibility(View.GONE);
            authButton.setEnabled(true);
        }
    }
}