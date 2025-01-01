plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
    alias(libs.plugins.kmp.nativecoroutines)
    alias(libs.plugins.devtools.ksp)
}

kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation(libs.kermit)
        }
    }

    sourceSets.androidMain {
        dependencies {}
    }

    sourceSets.iosMain {
        dependencies {}
    }

}