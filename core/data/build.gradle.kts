plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
}

kotlin {

    sourceSets.commonMain {
        dependencies {
            implementation(projects.core.common)
            implementation(projects.core.model)
            implementation(projects.core.database)
            implementation(projects.core.preference)

            //coroutine
            implementation(libs.kotlinx.coroutines.core)
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