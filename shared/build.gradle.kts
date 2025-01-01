import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.touchlab.skie)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kakapo.xcFramework)
}

kotlin {
    val xcFramework = XCFramework("Oakane")

    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.withType<Framework> {
            baseName = "Oakane"

            isStatic = !debuggable
            linkerOpts.add("-lsqlite3")
            freeCompilerArgs += if (debuggable) "-Xadd-light-debug=enable" else ""

            export(projects.core.common)

            xcFramework.add(this)
        }
    }

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.lifecycle.viewmodel)

            api(projects.core.database)
            api(projects.core.model)
            api(projects.core.common)

            api(project.dependencies.platform(libs.koin.bom))

            implementation(libs.datastore)
            implementation(libs.datastore.preferences)

            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            implementation(libs.kermit)
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
