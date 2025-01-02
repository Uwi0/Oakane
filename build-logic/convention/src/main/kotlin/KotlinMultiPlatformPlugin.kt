import com.android.build.gradle.LibraryExtension
import com.kakapo.app.buildlogic.configureKotlinAndroid
import com.kakapo.app.buildlogic.configureKotlinMultiplatform
import com.kakapo.app.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


class KotlinMultiPlatformPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager){
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
            apply(libs.findPlugin("dependency-analysis").get().get().pluginId)
        }
        
        extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
        extensions.configure<LibraryExtension>(::configureKotlinAndroid)

        target.afterEvaluate {
            // Remove log pollution until Android support in KMP improves.
            extensions.findByType<KotlinMultiplatformExtension>()?.let { kmpExt ->
                kmpExt.sourceSets.removeAll {
                    setOf(
                        "androidAndroidTestRelease",
                        "androidTestFixtures",
                        "androidTestFixturesDebug",
                        "androidTestFixturesRelease",
                        "androidTestFixturesDemo",
                    ).contains(it.name)
                }
            }
        }
    }
}