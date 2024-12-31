plugins {
    `kotlin-dsl`
}

group = "com.kakapo.app.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.kakapo.app.kotlinMultiplatform"
            implementationClass = "KotlinMultiPlatformPlugin"
        }
    }
}
