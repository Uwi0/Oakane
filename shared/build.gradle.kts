import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
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

            api(libs.androidx.lifecycle.viewmodel)

            api(project.dependencies.platform(libs.koin.bom))

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kermit)
            implementation(projects.core.database)
        }
        sourceSets.androidMain.dependencies {
            implementation(libs.koin.android)
        }
        sourceSets.iosMain.dependencies {
        }
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
