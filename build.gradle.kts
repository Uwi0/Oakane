plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.appCash.sqlDelight) apply false
    alias(libs.plugins.touchlab.skie) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.kmp.nativecoroutines) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.dependency.analysis) apply false
}