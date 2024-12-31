import com.android.build.gradle.LibraryExtension
import com.kakapo.app.buildlogic.configureKotlinAndroid
import com.kakapo.app.buildlogic.configureKotlinMultiplatform
import com.kakapo.app.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


class KotlinMultiPlatformPlugin: Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager){
            apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            apply(libs.findPlugin("androidLibrary").get().get().pluginId)
            apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
        }
        
        extensions.configure<KotlinMultiplatformExtension>(::configureKotlinMultiplatform)
        extensions.configure<LibraryExtension>(::configureKotlinAndroid)
    }
}