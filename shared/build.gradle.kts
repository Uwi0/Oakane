
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.touchlab.skie)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

version = "1.0.0"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    val xcf = XCFramework()
    val iosTargets = listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    iosTargets.forEach {
        it.binaries.framework {
            baseName = "Shared"
            export(projects.core.common)
            export(projects.core.model)
            xcf.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)

            api(projects.core.model)
            api(projects.core.common)
            implementation(projects.core.database)
            implementation(projects.core.preference)
            implementation(projects.core.data)
            implementation(projects.core.domain)

            api(project.dependencies.platform(libs.koin.bom))

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

            implementation(libs.kermit)
        }
        sourceSets.androidMain.dependencies {
            implementation(libs.koin.android)
        }
        sourceSets.iosMain.dependencies {
            implementation(projects.core.common)
        }
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}
