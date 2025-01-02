plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlinCocoapods)
}

version = "1.0.0"

kotlin {

    cocoapods {
        version = "1.0.0"
        summary = "shared library for iOS"
        homepage = "Link to a Kotlin/Native module homepage"
        ios.deploymentTarget = "17.0"
        framework {
            baseName = "SharedCommon"
        }
    }

    sourceSets.commonMain {
        dependencies {
            implementation(libs.kermit)
        }
    }

    sourceSets.androidMain {
        dependencies {}
    }

    sourceSets.iosMain {
        dependencies {}
    }

}