plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.appdistribution)
}

android {
    namespace = "com.example.kwaaimancarservices"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.kwaaimancarservices"
        minSdk = 24
        targetSdk = 34
        versionCode = 1000001  // Version 1.0.1
        versionName = "1.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // Signing configuration for release builds
    signingConfigs {
        create("release") {
            storeFile = file(System.getenv("KWAAIMAN_RELEASE_STORE_FILE") ?: rootProject.file("kwaaiman-release-key.keystore"))
            storePassword = System.getenv("KWAAIMAN_RELEASE_STORE_PASSWORD") ?: (project.findProperty("KWAAIMAN_RELEASE_STORE_PASSWORD") as? String ?: "")
            keyAlias = System.getenv("KWAAIMAN_RELEASE_KEY_ALIAS") ?: (project.findProperty("KWAAIMAN_RELEASE_KEY_ALIAS") as? String ?: "kwaaiman-key-alias")
            keyPassword = System.getenv("KWAAIMAN_RELEASE_KEY_PASSWORD") ?: (project.findProperty("KWAAIMAN_RELEASE_KEY_PASSWORD") as? String ?: "")
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
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    // Core Android libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Google Maps & Location Services
    implementation("com.google.android.gms:play-services-maps:20.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.libraries.places:places:5.1.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.9.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    // Networking
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")

    // Image Loading
    implementation("com.github.bumptech.glide:glide:5.0.5")

    // Payment Processing Libraries
    implementation("com.stripe:stripe-android:22.7.0")
    // NOTE: For PayPal integration, you would add the PayPal SDK
    // For South African payment methods like PayFast, SnapScan, Zapper, you would add their specific SDKs when available

    // RecyclerView and CardView
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.cardview:cardview:1.0.0")

    // ViewPager2 for onboarding
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // Work Manager for background tasks
    implementation("androidx.work:work-runtime:2.11.1")

    // Room Database
    implementation("androidx.room:room-runtime:2.8.4")
    annotationProcessor("androidx.room:room-compiler:2.8.4")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.10.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.10.0")

    // Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.2.0")

    // Lottie for animations
    implementation("com.airbnb.android:lottie:6.7.1")

    // Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


}
