package com.example.kwaaimancarservices;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class KwaaimanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
    }
}