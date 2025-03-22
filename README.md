# Oakane - Cross-Platform Finance App

Oakane is a finance management app built with **Kotlin Multiplatform (KMP)**, leveraging **Jetpack Compose** for Android and **SwiftUI** for iOS. The name "Oakane" is a combination of "oak" (symbolizing growth) and "okane" (meaning money in Japanese). This project explores KMP for seamless cross-platform development.

## 🚀 Features
- **Expense Tracking** - Log and categorize your expenses.
- **Custom Category** - Create your custom Category.
- **Monthly budgetg** - Add limits how much you can spend.
- **Category Limit** - Add limits how much you can spend in every caetgory.
- **Exclude from Budget** - Option to exclude unexpected expenses from the monthly budget.
- **Goalt** Set financial goals and track your progress toward saving or spending targets..
- **Wallet Management** - Create and manage multiple wallets.
- **Wallet Logs** - Log every transctions in your wallet.
- **Reportss** - Get insights into your spending habits with detailed charts and analytics. Export reports as CSV for further analysis.
- **Dark Mode** – A sleek, eye-friendly dark theme for better usability in low-light environments.
- **Currency Selection** – Choose your preferred currency for a personalized finance tracking experience.
- **Recurring Monthly** Budget – Automatically set and reset your spending limits each month.
- **Recurring Category Limit** – Maintain control over specific spending categories with automatic monthly limits.
- **Backup & Import Data** – Securely back up your financial data and restore it when needed.
- **Cross-Platform Support** - Android and iOS versions share the same features.

## 🏗️ Project Structure
```
├── LICENSE
├── README.md
├── build
│   ├── kotlin
│   ├── reports
│   └── tmp
├── build-logic
│   ├── convention
│   ├── gradle.properties
│   └── settings.gradle.kts
├── build.gradle.kts
├── composeApp
│   ├── build
│   ├── build.gradle.kts
│   ├── google-services.json
│   ├── prod
│   ├── proguard-rules.pro
│   ├── release
│   └── src
├── core
│   ├── common
│   ├── data
│   ├── database
│   ├── domain
│   ├── model
│   └── preference
├── gradle
│   ├── libs.versions.toml
│   └── wrapper
├── gradle.properties
├── gradlew
├── gradlew.bat
├── iosApp
│   ├── Configuration
│   ├── iosApp
│   ├── iosApp.xcodeproj
│   └── oakane.xcodeproj
├── local.properties
├── settings.gradle.kts
└── shared
    ├── build
    ├── build.gradle.kts
    └── src
```

## 📋 Project Requirements

## **Android**
- Compile SDK: 35
- Minimum SDK: 26
- Target SDK: 35
- Java Version: 17


## **iOS**
- Minimum Deployment Target: iOS 17.6
- Xcode Version: 16.2 (Recommended)
- Swift Version: 6.0.3

# Tech Stack

## **Languages & Frameworks**
- **Kotlin Multiplatform (KMP)** - Shared business logic across Android and iOS.
- **Jetpack Compose** - Declarative UI for Android.
- **SwiftUI** - Declarative UI for iOS.
- **SQLDelight** - Kotlin Multiplatform database with type-safe queries.
- **Kotlin Coroutines** - Asynchronous programming.
- **Kotlin Serialization** - JSON serialization for Kotlin.
- **Koin** - Dependency Injection framework.

## **Android**
- **Accompanist** - Jetpack Compose utilities.
- **AndroidX CameraX** - Camera support.
- **Datastore Preferences** - Lightweight data storage.
- **Material Design Components** - Google's Material UI.
- **Splash Screen API** - Native splash screen.

## **iOS**
- **SwiftUI** - Native UI framework.
- **Skie** - Seamlessly transform Kotlin sealed classes into Swift enums.
- **KMP-NativeCoroutines** - Bridging Kotlin Coroutines to Swift Concurrency.

## **Build & Dependency Management**
- **Gradle (Kotlin DSL)** - Build system.
- **KSP (Kotlin Symbol Processing)** - Annotation processing.
- **Firebase Crashlytics** - Crash reporting.
- **Google Services Plugin** - Firebase integration.
- **Dependency Analysis Plugin** - Detect unused dependencies.

## **Testing**
- **Kotest** - Test framework for Kotlin Multiplatform.
- **JUnit** - Standard testing framework.
- **Espresso** - UI testing for Android.

## **Debugging & Monitoring**
- **Kermit** - Logging for KMP.
- **LeakCanary** - Memory leak detection.
- **Firebase Crashlytics** - Error tracking.

## **Platform Compatibility**
- **Android:** Min SDK 26, Target SDK 35
- **iOS:** Deployment target 17.6, Swift 6.0.3, Xcode 16.2

## 📖 How to Run
### **download google services.json for android**
1. Go to Firebase Console.
2. Click Add App → Select Android
3. Enter the package name:com.kakapo.oakane.demo
4. Generate SHA-1 using:./gradlew signingReport
5. Paste the SHA-1 in Firebase.
6. Download the google-services.json file.
7. Place the file inside:composeApp/src/androidMain/
Ensure the file is present before building the project, as it will be automatically detected.
### **Android**
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/oakane.git
   cd oakane
   ```
2. Open in **Android Studio**
3. Sync Gradle and run the project on an emulator or physical device

### **iOS**
1. Clone the repository
2. Open `iosApp/iosApp.xcodeproj` in **Xcode**
3. Run on a simulator or physical device

## 🔮 Roadmap
- **Transaction Location** - Log expenses with location metadata
- **Reminders** - Set alerts for recurring expenses

## 📌 Useful Links
- **Google Play Store**: [Download Here](https://play.google.com/store/apps/details?id=com.kakapo.oakane)
- **GitHub Repository**: [Check It Out](https://github.com/yourusername/oakane)
- **Figma Design**: [View the Prototype](https://www.figma.com/community/file/1469282312265944668/oakane)
- **Kotlin Multiplatform Docs**: [Read More](https://kotlinlang.org/docs/multiplatform.html)

---
⚒️ **This repository is my playground for experimenting with KMP. Expect bugs and ongoing improvements!**



