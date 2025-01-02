plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {

    sourceSets.commonMain {
        dependencies {
            implementation(libs.kotlin.stdlib)
            // Add KMP dependencies here
        }
    }

    sourceSets.androidMain {
        dependencies {
        }
    }

    sourceSets.iosMain {
        dependencies {
        }
    }
}