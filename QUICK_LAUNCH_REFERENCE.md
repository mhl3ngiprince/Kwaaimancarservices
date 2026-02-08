# Quick Launch Command Reference

## 🚀 FASTEST PATH TO LAUNCH

### Step 1: Setup (Do Once)
```powershell
# Generate signing keystore
keytool -genkey -v -keystore kwaaiman-release-key.keystore -alias kwaaiman-key-alias -keyalg RSA -keysize 2048 -validity 10000

# Update gradle.properties with signing credentials
# (See COMPLETE_APP_STORE_SUBMISSION_GUIDE.md for details)
```

### Step 2: Build Release AAB
```powershell
cd C:\Users\admin\AndroidStudioProjects\Kwaaimancarservices

# Clean previous builds
.\gradlew clean

# Build App Bundle (recommended for Google Play)
.\gradlew bundleRelease

# Output: app/build/outputs/bundle/release/app-release.aab
```

### Step 3: Verify Signing
```powershell
# Check APK signature (if building APK instead)
jarsigner -verify -verbose app/build/outputs/apk/release/app-release.apk

# Output should show "jar verified" ✅
```

### Step 4: Submit to Google Play Store
```
1. Go to https://play.google.com/console
2. Select your app
3. Go to Release → Production
4. Create new release
5. Upload app-release.aab
6. Review and submit
7. App goes live in 2-24 hours
```

---

## 📦 BUILD OPTIONS

### Debug Build (For Local Testing)
```powershell
.\gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

### Release APK (Standalone)
```powershell
.\gradlew assembleRelease
# Output: app/build/outputs/apk/release/app-release.apk
```

### Release AAB (Recommended for Play Store)
```powershell
.\gradlew bundleRelease
# Output: app/build/outputs/bundle/release/app-release.aab
```

---

## 🔑 SIGNING CREDENTIALS REFERENCE

**Keystore Details:**
- File: `kwaaiman-release-key.keystore`
- Alias: `kwaaiman-key-alias`
- Key Size: 2048-bit RSA
- Validity: 10000 days (27+ years)

**gradle.properties:**
```properties
KWAAIMAN_RELEASE_STORE_FILE=kwaaiman-release-key.keystore
KWAAIMAN_RELEASE_KEY_ALIAS=kwaaiman-key-alias
KWAAIMAN_RELEASE_STORE_PASSWORD=your_password
KWAAIMAN_RELEASE_KEY_PASSWORD=your_password
```

---

## 📋 CRITICAL REQUIREMENTS

Before each build, ensure:
- [ ] Keystore file exists at specified path
- [ ] gradle.properties has correct passwords
- [ ] google-services.json is in app/ directory
- [ ] API key in AndroidManifest.xml is valid
- [ ] All dependencies resolve correctly

**Verify dependencies:**
```powershell
.\gradlew dependencies
```

---

## 🐛 COMMON ISSUES & QUICK FIXES

| Issue | Quick Fix |
|-------|-----------|
| Keystore not found | Verify file path and gradle.properties |
| Wrong password | Confirm password matches keystore generation |
| Build fails | Run `.\gradlew clean` then retry |
| ProGuard errors | Check proguard-rules.pro syntax |
| Firebase errors | Verify google-services.json exists and is valid |

---

## 📊 FILE SIZES

Expected output sizes:
- Debug APK: 80-120 MB
- Release APK: 50-90 MB
- Release AAB: 40-80 MB

Actual sizes depend on included resources and assets.

---

## 🎯 NEXT IMMEDIATE ACTIONS

1. **Generate Keystore** (if not already done)
   ```powershell
   keytool -genkey -v -keystore kwaaiman-release-key.keystore -alias kwaaiman-key-alias -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Configure gradle.properties**
   ```properties
   KWAAIMAN_RELEASE_STORE_FILE=kwaaiman-release-key.keystore
   KWAAIMAN_RELEASE_KEY_ALIAS=kwaaiman-key-alias
   KWAAIMAN_RELEASE_STORE_PASSWORD=your_store_password
   KWAAIMAN_RELEASE_KEY_PASSWORD=your_key_password
   ```

3. **Build Release AAB**
   ```powershell
   .\gradlew bundleRelease
   ```

4. **Submit to Google Play Store**
   Visit https://play.google.com/console and follow on-screen instructions

---

## 📚 FULL DOCUMENTATION

For detailed information, see:
- `COMPLETE_APP_STORE_SUBMISSION_GUIDE.md` - Full submission walkthrough
- `PRE_LAUNCH_VERIFICATION_CHECKLIST.md` - Quality assurance checklist
- `QUALITY_ASSURANCE_AND_LAUNCH_GUIDE.md` - Comprehensive launch guide
- `RELEASE_BUILD_INSTRUCTIONS.md` - Original build instructions

---

**Last Updated:** February 8, 2026

