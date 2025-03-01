plugins {
    alias(libs.plugins.appCash.sqlDelight)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
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
            srcDirs("src/commonMain/sqldelight")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            verifyMigrations.set(true)
            version = 2
        }
    }
}
