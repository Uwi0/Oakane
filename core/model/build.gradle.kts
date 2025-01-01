plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {
    sourceSets.commonMain {
        dependencies {
            implementation(projects.core.common)
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