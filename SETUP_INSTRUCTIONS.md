# Kwaaiman Ride-Hailing App Setup Instructions

## Google Maps API Key Setup

### Step 1: Get Google Maps API Key

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the following APIs:
    - Maps SDK for Android
    - Places API
    - Directions API
    - Geocoding API
4. Create credentials → API Key
5. Restrict the API key to your app's package name: `com.example.kwaaimancarservices`

### Step 2: Configure API Key

Replace `YOUR_GOOGLE_MAPS_API_KEY_HERE` in `AndroidManifest.xml` with your actual API key:

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="AIzaSyC..." />
```

## Firebase Setup (Optional for full functionality)

### Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project
3. Add Android app with package name: `com.example.kwaaimancarservices`
4. Download `google-services.json` and place in `app/` folder

### Step 2: Enable Firebase Services

- Authentication (Phone/Email)
- Cloud Firestore (for user data and trip history)
- Cloud Messaging (for push notifications)

## Build Instructions

1. **Open in Android Studio**
   ```bash
   # Open the project in Android Studio
   # File → Open → Select project folder
   ```

2. **Sync Project**
   ```bash
   # In Android Studio: File → Sync Project with Gradle Files
   ```

3. **Build APK**
   ```bash
   # Terminal in project root:
   ./gradlew assembleDebug
   ```

## App Features

✅ **Complete Ride-Hailing Platform**

- Multi-service options (Ride, Delivery, Scheduled)
- Vehicle categories (Economy, Comfort, Premium, XL, Motorcycle)
- Real-time trip tracking
- Driver-passenger communication
- Payment integration ready
- Emergency support
- Driver registration and dashboard
- Trip history and user profiles

✅ **South African Localized**

- ZAR currency
- South African locations and phone numbers
- Emergency services integration (10111)

## App Architecture

```
├── activities/
│   ├── SplashActivity
│   ├── OnboardingActivity
│   ├── AuthActivity
│   ├── MainActivity (Main ride booking interface)
│   ├── LocationSearchActivity
│   ├── RideBookingActivity
│   ├── TripTrackingActivity
│   ├── ProfileActivity
│   ├── TripHistoryActivity
│   ├── PaymentMethodsActivity
│   ├── SettingsActivity
│   ├── SupportActivity
│   ├── DriverRegistrationActivity
│   └── DriverDashboardActivity
├── models/
│   ├── Location
│   └── VehicleType
├── adapters/
│   ├── LocationSearchAdapter
│   └── VehicleTypeAdapter (to be created)
└── services/
    ├── FirebaseMessagingService
    └── LocationTrackingService
```

## Note

This is a complete ride-hailing app similar to Uber, Bolt, and Maxim. All campus-specific
functionality has been removed and replaced with general city-wide transportation services.