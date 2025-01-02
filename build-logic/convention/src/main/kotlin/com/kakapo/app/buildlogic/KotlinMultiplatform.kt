package com.kakapo.app.buildlogic

import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

internal fun Project.configureKotlinMultiplatform(
    extension: KotlinMultiplatformExtension
) = extension.apply {
    applyDefaultHierarchyTemplate()

    jvmToolchain(17)
    if (pluginManager.hasPlugin("com.android.library")) {
        androidTarget()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

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

    targets.withType<KotlinNativeTarget>().configureEach {

        binaries.all {
            linkerOpts("-lsqlite3")
        }

        compilations.configureEach {
            compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xallocator=custom")
                    freeCompilerArgs.add("-XXLanguage:+ImplicitSignedToUnsignedIntegerConversion")
                    freeCompilerArgs.add("-Xadd-light-debug=enable")
                    freeCompilerArgs.add("-Xexpect-actual-classes")

                    freeCompilerArgs.addAll(
                        "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                        "-opt-in=kotlinx.cinterop.BetaInteropApi",
                    )
                }
            }
        }
    }
}
