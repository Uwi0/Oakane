plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kmp.nativecoroutines)
}

kotlin {

    sourceSets.commonMain {
        dependencies {
            implementation(projects.core.common)
            implementation(projects.core.model)
            implementation(projects.core.database)
            implementation(projects.core.preference)
        }
    }

    sourceSets.androidMain {
        dependencies {

        }
    }

    sourceSets.iosMain {
        dependencies {

        }
    }

}