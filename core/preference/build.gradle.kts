plugins {
    alias(libs.plugins.kakapo.kotlinMultiplatform)
}

kotlin {

    sourceSets.commonMain {
        dependencies {
            implementation(libs.datastore)
            implementation(libs.datastore.preferences)
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