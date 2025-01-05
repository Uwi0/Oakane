plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kmp.nativecoroutines)
}

kotlin {

    sourceSets.commonMain {
        dependencies {
            implementation(projects.core.model)
            implementation(projects.core.common)
            implementation(projects.core.data)
        }
    }

    sourceSets.androidMain {
        dependencies {}
    }

    sourceSets.iosMain {
        dependencies {}
    }

}