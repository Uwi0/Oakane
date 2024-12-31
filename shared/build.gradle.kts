import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.appCash.sqlDelight)
    alias(libs.plugins.touchlab.skie)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.sqldelight.coroutines)

            api(libs.androidx.lifecycle.viewmodel)

            api(project.dependencies.platform(libs.koin.bom))

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kermit)
        }
        sourceSets.androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
            implementation(libs.koin.android)
        }
        sourceSets.iosMain.dependencies {
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

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
