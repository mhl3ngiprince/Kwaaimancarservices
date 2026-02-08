# App Icon Creation Instructions

## Adaptive Icons Requirements for App Store Submission

For your Kwaaiman Car Services app to be ready for app stores, you need to create proper adaptive icons with the following specifications:

### 1. Adaptive Icon Dimensions
- Foreground layer: 108x108 dp (drawn to 136x136 px)
- Background layer: 108x108 dp (drawn to 136x136 px)
- Final icon displayed: 72x72 dp (at 1.5x density)

### 2. Icon Sizes for Different Densities
- mipmap-mdpi: 48x48 px
- mipmap-hdpi: 72x72 px
- mipmap-xhdpi: 96x96 px
- mipmap-xxhdpi: 144x144 px
- mipmap-xxxhdpi: 192x192 px

### 3. How to Create/Replace Icons

1. **Using Android Studio:**
   - Right-click on `app/src/main/res` folder
   - Select `New` → `Image Asset`
   - Choose `Launcher Icons (Adaptive and Legacy)`
   - Select your source image or text
   - Customize as needed
   - Click `Next` and then `Finish`

2. **Manually:**
   - Prepare your icon assets in PNG format at various densities
   - Place them in the respective folders:
     - `app/src/main/res/mipmap-mdpi/ic_launcher.png`
     - `app/src/main/res/mipmap-hdpi/ic_launcher.png`
     - `app/src/main/res/mipmap-xhdpi/ic_launcher.png`
     - `app/src/main/res/mipmap-xxhdpi/ic_launcher.png`
     - `app/src/main/res/mipmap-xxxhdpi/ic_launcher.png`
   - Also update the round launcher versions in `-round` subdirectories

### 4. Current Icon Reference
Currently, your app uses `kwaaiman_logo` as the icon. You should update this to follow adaptive icon guidelines.

### 5. Design Guidelines
- Use a 48dp radius circle for the safe zone
- Keep important imagery within the safe zone
- Maintain consistent appearance across different adaptive icon masks
- Use vector drawables when possible for scalability

### 6. Verification
After creating the icons, verify that:
- All density folders have appropriately sized icons
- The app displays the icon correctly on different devices
- The icon meets app store guidelines (no transparency issues, appropriate branding)

### 7. Recommended Tools
- Adobe Illustrator/Photoshop
- Android Asset Studio (online tool)
- Sketch
- Figma

For immediate testing, you can temporarily use a standard Android launcher icon while preparing your custom adaptive icon set.