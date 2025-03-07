plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotest.multiplatform)
}

kotlin {

    sourceSets.commonMain {
        dependencies {
            implementation(projects.core.model)
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(libs.kermit)
        }
    }

    sourceSets.androidMain {
        dependencies {}
    }

    sourceSets.iosMain {
        dependencies {}
    }

    sourceSets.commonTest {
        dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotest.framework)
        }
    }

}