plugins {
    alias(libs.plugins.appCash.sqlDelight)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
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
            srcDirs.setFrom("src/commonMain/sqldelight")
            packageName.set("com.kakapo")

            schemaOutputDirectory = file("src/commonMain/sqldelight/databases")
            verifyMigrations = true
            version = 2
        }
    }
}
