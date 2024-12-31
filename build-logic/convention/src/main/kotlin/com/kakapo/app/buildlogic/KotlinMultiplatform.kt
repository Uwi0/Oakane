package com.kakapo.app.buildlogic

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    applyDefaultHierarchyTemplate()

    jvmToolchain(17)
    if (pluginManager.hasPlugin("com.android.library")) {
        androidTarget()
    }

    listOf(
        iosArm64(),
        iosX64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = path.substring(1)
                .replace(":", "-")
                .replaceFirstChar { it.uppercase() }
        }
    }

    sourceSets.apply {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.findLibrary("koin-bom").get()))
            implementation(libs.findLibrary("koin-core").get())
            implementation(libs.findLibrary("kotlinx-datetime").get())
            implementation(libs.findLibrary("kotlinx-serialization").get())
        }

        androidMain.dependencies {
            implementation(project.dependencies.platform(libs.findLibrary("koin-bom").get()))
            implementation(libs.findLibrary("koin-android").get())
        }

        all {
            languageSettings {
                listOf(
                    "kotlin.RequiresOptIn",
                    "kotlin.experimental.ExperimentalObjCName",
                    "kotlin.time.ExperimentalTime",
                    "kotlinx.coroutines.ExperimentalCoroutinesApi",
                ).forEach { optIn(it) }
            }
        }
    }
}
