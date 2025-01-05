plugins {
    alias(libs.plugins.appCash.sqlDelight)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation(libs.sqldelight.coroutines)
        }
    }

    sourceSets.androidMain {
        dependencies {
            implementation(libs.sqldelight.android.driver)
        }
    }

    sourceSets.iosMain {
        dependencies {
            implementation(libs.sqldelight.navtive.driver)
        }
    }
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.kakapo")
        }
    }
}
