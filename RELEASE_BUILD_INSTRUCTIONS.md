# Release Build Instructions for Kwaaiman Car Services

## Creating a Signed APK/AAB for App Stores

### 1. Generate a Keystore

Run the following command in your terminal to generate a keystore:

**Windows:**
```bash
keytool -genkey -v -keystore kwaaiman-release-key.keystore -alias kwaaiman-key-alias -keyalg RSA -keysize 2048 -validity 10000
```

**macOS/Linux:**
```bash
keytool -genkey -v -keystore kwaaiman-release-key.keystore -alias kwaaiman-key-alias -keyalg RSA -keysize 2048 -validity 10000
```

### 2. Store the Keystore Safely

- Store the keystore file in a safe, secure place
- Remember the password and alias name
- Never commit the keystore file to version control

### 3. Configure Gradle for Signing

Create or update `gradle.properties` in your project root:

```properties
# Kwaaiman Production Release Properties
KWAAIMAN_RELEASE_STORE_FILE=kwaaiman-release-key.keystore
KWAAIMAN_RELEASE_KEY_ALIAS=kwaaiman-key-alias
KWAAIMAN_RELEASE_STORE_PASSWORD=your_store_password
KWAAIMAN_RELEASE_KEY_PASSWORD=your_key_password
```

Then add this signing configuration to your `app/build.gradle.kts`:

```kotlin
android {
    // ... other configurations ...

    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KWAAIMAN_RELEASE_STORE_FILE") ?: rootProject.file("kwaaiman-release-key.keystore"))
            storePassword = System.getenv("KWAAIMAN_RELEASE_STORE_PASSWORD") ?: getProperty("KWAAIMAN_RELEASE_STORE_PASSWORD")
            keyAlias = System.getenv("KWAAIMAN_RELEASE_KEY_ALIAS") ?: getProperty("KWAAIMAN_RELEASE_KEY_ALIAS")
            keyPassword = System.getenv("KWAAIMAN_RELEASE_KEY_PASSWORD") ?: getProperty("KWAAIMAN_RELEASE_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        // ... debug configuration ...
    }
}
```

### 4. Build Release APK

**Using Gradle (command line):**
```bash
cd C:\Users\admin\AndroidStudioProjects\Kwaaimancarservices
./gradlew assembleRelease
```

The APK will be located at:
`app/build/outputs/apk/release/app-release.apk`

### 5. Build App Bundle (AAB) - Recommended for Google Play

**Using Gradle (command line):**
```bash
cd C:\Users\admin\AndroidStudioProjects\Kwaaimancarservices
./gradlew bundleRelease
```

The AAB will be located at:
`app/build/outputs/bundle/release/app-release.aab`

### 6. Using Android Studio

1. Open Android Studio
2. Go to `Build` → `Generate Signed Bundle / APK`
3. Select `APK` or `Android App Bundle`
4. Click `Next`
5. Click `Create new...` to create a new keystore or select an existing one
6. Fill in the keystore information
7. Select `release` build type
8. Click `Finish`

### 7. Verify the Release Build

Before uploading to app stores, verify your release build:

```bash
# Check if the APK is properly signed
jarsigner -verify -verbose -certs app/build/outputs/apk/release/app-release.apk

# Or using apksigner (part of Android SDK)
apksigner verify app/build/outputs/apk/release/app-release.apk
```

### 8. Test the Release Build

Install the release APK on a test device to ensure everything works properly:

```bash
adb install app/build/outputs/apk/release/app-release.apk
```

### 9. Google Play Store Upload

1. Create a developer account on Google Play Console
2. Register your application
3. Upload the AAB file (recommended) or APK
4. Fill in the store listing information
5. Upload screenshots and promotional materials
6. Define app content rating
7. Set pricing and distribution
8. Roll out to testers first (internal testing or closed testing)
9. Publish to production after testing

### 10. Other App Stores

For other app stores (Samsung Galaxy Store, Huawei AppGallery, etc.), follow their specific submission guidelines with your release APK.

### 11. Post-Launch Monitoring

- Monitor crash reports in Google Play Console
- Track user reviews and feedback
- Monitor app performance and user retention
- Plan for regular updates based on user feedback

### 12. Important Notes

- Always test the release build thoroughly before publishing
- Ensure your Google Maps API key is restricted to your production package name
- Verify all Firebase services are properly configured for production
- Make sure all third-party services are ready for production traffic
- Have a support system ready for user inquiries
- Comply with local regulations for transportation services in your target markets