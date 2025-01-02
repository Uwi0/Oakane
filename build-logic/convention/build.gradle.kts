import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    `kotlin-dsl`
}

group = "com.kakapo.app.buildlogic"

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.withType<JavaCompile>().configureEach {
    sourceCompatibility = "17"
    targetCompatibility = "17"
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.dependency.analysis.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kotlinMultiplatform") {
            id = "com.kakapo.app.kotlinMultiplatform"
            implementationClass = "KotlinMultiPlatformPlugin"
        }
        register("xcframework") {
            id = "com.kakapo.app.xcframework"
            implementationClass = "XCFrameworkPlugin"
        }
    }
}
