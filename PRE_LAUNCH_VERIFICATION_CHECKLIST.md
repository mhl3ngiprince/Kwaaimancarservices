# Kwaaiman Car Services - Pre-Launch Verification Checklist

## Final Quality Assurance Checklist

### ✅ BUILD CONFIGURATION VERIFICATION

#### 1. App Identification
- [x] Package Name: `com.kwaaimancarservices.rides`
- [x] Application ID: `com.kwaaimancarservices.rides`
- [x] Version Code: 1000001 (represents v1.0.1)
- [x] Version Name: "1.0.1"
- [x] Min SDK: 25 (Android 7.0 Nougat)
- [x] Target SDK: 35 (Android 15)
- [x] Compile SDK: 35

#### 2. Release Build Configuration
- [x] Code Shrinking Enabled: YES (isMinifyEnabled = true)
- [x] Resource Shrinking Enabled: YES (isShrinkResources = true)
- [x] ProGuard Obfuscation: Enabled
- [x] Signing Configuration: Added to build.gradle.kts

#### 3. Debug Build Configuration
- [x] Debug Build Suffix: ".debug"
- [x] Version Name Suffix: "-debug"
- [x] Code Shrinking: Disabled for faster builds

---

## PRE-LAUNCH REQUIREMENTS

### CRITICAL - MUST COMPLETE BEFORE SUBMITTING

#### 1. Google Play Console Setup
- [ ] Create Google Play Developer Account ($25 USD one-time fee)
- [ ] Create new application in Google Play Console
- [ ] Get SHA-1 fingerprint from keystore (needed for Firebase)

#### 2. Keystore Generation & Configuration
**Command to generate keystore (run ONCE):**
```powershell
keytool -genkey -v -keystore kwaaiman-release-key.keystore -alias kwaaiman-key-alias -keyalg RSA -keysize 2048 -validity 10000
```

**After keystore is created:**
1. Store the keystore file securely (NEVER commit to Git)
2. Add to `.gitignore`: `*.keystore`
3. Configure in `gradle.properties`:
```properties
KWAAIMAN_RELEASE_STORE_FILE=kwaaiman-release-key.keystore
KWAAIMAN_RELEASE_KEY_ALIAS=kwaaiman-key-alias
KWAAIMAN_RELEASE_STORE_PASSWORD=your_store_password
KWAAIMAN_RELEASE_KEY_PASSWORD=your_key_password
```

#### 3. Firebase Configuration
- [ ] Create Firebase Project (https://console.firebase.google.com)
- [ ] Register app with package name: `com.kwaaimancarservices.rides`
- [ ] Download `google-services.json`
- [ ] Place in `app/` directory
- [ ] Enable services:
  - [x] Firebase Authentication
  - [x] Cloud Firestore Database
  - [x] Cloud Messaging (FCM)
  - [x] Firebase Analytics

#### 4. Google Maps API Key
- [ ] Obtain Google Maps API Key from Google Cloud Console
- [ ] Enable Maps SDK for Android
- [ ] Generate SHA-1 from keystore:
```powershell
keytool -list -v -keystore kwaaiman-release-key.keystore -alias kwaaiman-key-alias
```
- [ ] Add package name and SHA-1 to API key restrictions
- [ ] Replace placeholder in AndroidManifest.xml:
```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_REAL_API_KEY_HERE" />
```

---

## FUNCTIONAL TESTING CHECKLIST

### App Startup & Navigation
- [ ] App launches from splash screen
- [ ] Splash screen displays for ~2 seconds
- [ ] Navigation to onboarding/authentication works
- [ ] Bottom navigation works correctly
- [ ] All activities load without crashes

### Authentication & User Accounts
- [ ] User registration works
- [ ] Email verification works (if enabled)
- [ ] User login works
- [ ] User logout works
- [ ] Session persistence works

### Ride Booking Features
- [ ] Location search functionality works
- [ ] Pickup location setting works
- [ ] Dropoff location setting works
- [ ] Ride type selection works
- [ ] Price estimation displays
- [ ] Ride booking submission works

### Maps & Location Services
- [ ] Google Maps loads correctly
- [ ] Location permission requests work
- [ ] Current location detection works
- [ ] Map navigation/zooming works
- [ ] Real-time location updates work

### Payment Integration
- [ ] Payment method selection works
- [ ] Stripe payment integration works (if enabled)
- [ ] Payment processing completes
- [ ] Receipt/confirmation displays

### Notifications
- [ ] Firebase Cloud Messaging works
- [ ] Push notifications display
- [ ] Notification taps navigate correctly
- [ ] Notification permissions handled

### Performance
- [ ] App launches quickly (<3 seconds)
- [ ] Navigation is smooth
- [ ] List scrolling is smooth (RecyclerView)
- [ ] No ANR (Application Not Responding) errors
- [ ] Memory usage is reasonable

### UI/UX Quality
- [ ] All text is readable
- [ ] Colors match branding
- [ ] Buttons are easily tappable
- [ ] No overlapping UI elements
- [ ] Responsive design works on different screen sizes
- [ ] Landscape orientation works (if applicable)

### Device Compatibility
- [ ] Tested on Android 7.0+ devices (minSdk 25)
- [ ] Tested on various screen sizes
- [ ] Tested on at least 3 different device models

### Permissions
- [ ] Location permission request works
- [ ] Camera permission request works (if applicable)
- [ ] Storage permission request works (if applicable)
- [ ] Phone permission request works
- [ ] Microphone permission request works (if applicable)
- [ ] App handles permission denials gracefully

---

## BUILD & COMPILATION

### Generate Signed Release APK
```powershell
cd C:\Users\admin\AndroidStudioProjects\Kwaaimancarservices
.\gradlew assembleRelease
```
- [ ] Build completes without errors
- [ ] APK generated at: `app/build/outputs/apk/release/app-release.apk`

### Generate App Bundle (AAB) - Recommended for Google Play
```powershell
cd C:\Users\admin\AndroidStudioProjects\Kwaaimancarservices
.\gradlew bundleRelease
```
- [ ] Build completes without errors
- [ ] AAB generated at: `app/build/outputs/bundle/release/app-release.aab`

### Verify Signed APK/AAB
```powershell
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```
- [ ] Signature verification passes
- [ ] Certificate information is correct

---

## STORE LISTING PREPARATION

### Required Assets for Google Play Store
- [ ] App Icon (512x512 PNG)
- [ ] Feature Graphic (1024x500 PNG)
- [ ] Screenshots (at least 2, max 8):
  - [ ] Pickup/Location selection
  - [ ] Ride booking
  - [ ] Trip tracking
  - [ ] Driver details
  - [ ] Additional features
- [ ] Short Description (50 characters max)
- [ ] Full Description (4000 characters max)
- [ ] Category: "Transportation"
- [ ] Content Rating Questionnaire completed

### Store Listing Content
- [ ] App Title: "Kwaaiman Car Services"
- [ ] Short Description: Concise, keyword-rich
- [ ] Full Description: Feature highlights, instructions
- [ ] Screenshots: High quality, representative
- [ ] Privacy Policy: Complete and linked
- [ ] Terms of Service: Complete and linked

### Compliance & Legal
- [ ] Privacy Policy created and accessible
- [ ] Terms & Conditions created
- [ ] Data Collection & Handling compliant with GDPR/CCPA
- [ ] No prohibited content
- [ ] Ads policy compliant (if applicable)

---

## RELEASE NOTES

### Version 1.0.1 Release Notes
**Features:**
- Ride booking and tracking
- Real-time driver location updates
- Multiple payment methods (Stripe integration)
- User ratings and reviews
- Trip history
- Firebase-based notifications

**Improvements:**
- Optimized code with ProGuard obfuscation
- Resource shrinking for smaller APK size
- Performance optimizations
- Security enhancements

**Bug Fixes:**
- Fixed package naming issues
- Resolved activity launcher configuration
- Improved error handling

---

## SUBMISSION CHECKLIST - GOOGLE PLAY STORE

### Before Submission
- [ ] All testing complete and passed
- [ ] Release APK/AAB builds successfully
- [ ] ProGuard obfuscation working
- [ ] Resource shrinking working
- [ ] All required assets uploaded
- [ ] Store listing complete and reviewed
- [ ] Content rating filled out
- [ ] Privacy policy URL set
- [ ] Pricing set ($0 for free, or applicable price)

### Google Play Console Steps
1. [ ] Go to https://play.google.com/console
2. [ ] Login with developer account
3. [ ] Select your application
4. [ ] Navigate to "Release" > "Production"
5. [ ] Create new release
6. [ ] Upload signed AAB file
7. [ ] Review app content and rating
8. [ ] Submit for review
9. [ ] Monitor for approval (typically 2-3 hours)

### After Submission
- [ ] Monitor review status
- [ ] Check for rejection reasons
- [ ] Address any issues
- [ ] Resubmit if needed
- [ ] Once approved, monitor crash reports
- [ ] Monitor user ratings

---

## OTHER APP STORE SUBMISSIONS

### Alternative App Stores (Optional)
- [ ] Samsung Galaxy Store
- [ ] Huawei App Gallery
- [ ] Amazon Appstore
- [ ] F-Droid (if open source)

---

## POST-LAUNCH MONITORING

### Day 1 After Launch
- [ ] Monitor crash rates in Firebase Crashlytics
- [ ] Check user feedback in reviews
- [ ] Monitor app performance metrics
- [ ] Check Firebase analytics

### First Week
- [ ] Respond to user reviews
- [ ] Address critical bugs
- [ ] Monitor user retention
- [ ] Analyze user behavior

### Ongoing
- [ ] Push notifications strategy
- [ ] Feature improvements based on feedback
- [ ] Regular security updates
- [ ] Dependency updates

---

## QUICK START - RELEASE BUILD COMMANDS

### 1. Clean Build
```powershell
.\gradlew clean
```

### 2. Build Release APK
```powershell
.\gradlew assembleRelease
```

### 3. Build Release AAB (Recommended)
```powershell
.\gradlew bundleRelease
```

### 4. Verify Signing
```powershell
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk
```

---

## IMPORTANT REMINDERS

⚠️ **BEFORE YOU BUILD:**
1. Ensure keystore is generated and passwords are configured
2. Ensure google-services.json is in place
3. Ensure API keys are correctly set
4. Run through functional testing

⚠️ **BEFORE YOU SUBMIT:**
1. Test the release APK on multiple devices
2. Verify all features work in release mode
3. Check ProGuard hasn't broken anything
4. Review store listing multiple times

⚠️ **SECURITY:**
1. NEVER commit keystore to Git
2. NEVER commit gradle.properties with passwords to Git
3. Use environment variables for sensitive data
4. Rotate API keys periodically

---

## CONTACT & SUPPORT

If you encounter any issues:
1. Check Android Studio build logs
2. Review Firebase console for errors
3. Check Google Play Console for submission issues
4. Review app reviews for user-reported problems

---

**Last Updated:** February 8, 2026
**App Version:** 1.0.1
**Status:** Ready for Pre-Launch Testing

