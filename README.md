This is a Kotlin Multiplatform project targeting Android, iOS.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
    - `commonMain` is for code that’s common for all targets.
    - Other folders are for Kotlin code that will be compiled for only the platform indicated in the
      folder name.
      For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
      `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for
  your project.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the
  platform-specific folders here too.

Learn more
about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

Oakane is a cross-platform finance app built using Kotlin Multiplatform with Jetpack Compose for Android and SwiftUI for iOS.
The name "Oakane" comes from a mix of "oak", symbolizing growth, and "okane", which means money in Japanese. 

⚒️ This repository is my playground where I experiment with different ideas and concepts. Expect there to be bugs and unfinished features from time to time. Enjoy the journey!

Img Source: https://www.pixiv.net/en/users/6567678
