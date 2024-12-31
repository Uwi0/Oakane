package com.kakapo.app.buildlogic

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    jvmToolchain(17)

    androidTarget()
    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
        }
    }

    sourceSets.apply {
        commonMain.dependencies {
            implementation(libs.findLibrary("koin-core").get())
            implementation(libs.findLibrary("kotlinx-datetime").get())
            implementation(libs.findLibrary("kotlinx-serialization").get())
        }


    }
}
