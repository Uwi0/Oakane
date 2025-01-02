import com.kakapo.app.buildlogic.NativeTargetType
import com.kakapo.app.buildlogic.XcodeBuildEnvironment.GROUP_NAME
import com.kakapo.app.buildlogic.XcodeBuildEnvironment.capitalizedName
import com.kakapo.app.buildlogic.XcodeBuildEnvironment.intermediatesDir
import com.kakapo.app.buildlogic.XcodeBuildEnvironment.nativeBuildTargetTypes
import com.kakapo.app.buildlogic.XcodeBuildEnvironment.nativeBuildType
import com.kakapo.app.buildlogic.XcodeBuildEnvironment.nativeTargetType
import com.kakapo.app.buildlogic.nativeFrameworks
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.deleteRecursively
import kotlin.io.path.listDirectoryEntries

interface XCFrameworkExtension {
    val frameworkName: Property<String>
    val outputPath: Property<String>
    val cleanIntermediate: Property<Boolean>
}

abstract class XCFrameworkPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        validateConfiguration()

        val extension = extensions.create<XCFrameworkExtension>("xcframework").apply {
            frameworkName.convention("Oakane.xcframework")
            outputPath.convention("ios/Modules/OakaneKit")
            cleanIntermediate.convention(true)
        }

        registerCopyXCFrameworkTask(
            nativeBuildType = nativeBuildType,
            targetType = nativeTargetType,
            extension = extension
        )

        nativeBuildTargetTypes.forEach { (buildType, targetType) ->
            registerCopyXCFrameworkTask(
                nativeBuildType = buildType,
                targetType = targetType,
                buildInfix = "${buildType.capitalizedName}${targetType.capitalizedName}",
                extension = extension
            )
        }
    }

    @OptIn(ExperimentalPathApi::class)
    private fun Project.registerCopyXCFrameworkTask(
        nativeBuildType: NativeBuildType,
        targetType: NativeTargetType,
        buildInfix: String = "",
        extension: XCFrameworkExtension
    ) : TaskProvider<Copy> {
        val multiplatformExtension = extensions.findByType<KotlinMultiplatformExtension>()
            ?: throw GradleException("The Kotlin Multiplatform extensions not found, apply plugin kotlin multiplatform first")

        val projectName = name
        val projectDir = rootProject.projectDir

        val assembleXCFrameworkTask = tasks.register<XCFrameworkTask>("assemble${buildInfix}XCFramework") {
            group = GROUP_NAME
            buildType = nativeBuildType

            val frameworks = multiplatformExtension.nativeFrameworks(nativeBuildType, targetType.targets)
            from(*frameworks.toTypedArray())
        }

        return tasks.register<Copy>("copy${buildInfix}XCFramework") {
            group = GROUP_NAME
            description = "Copies the $buildInfix XCFramework to ${extension.outputPath.get()}"

            val outputDir = projectDir.resolve(
                "${extension.outputPath.get()}/${extension.frameworkName.get()}"
            )

            dependsOn(assembleXCFrameworkTask)

            from(assembleXCFrameworkTask.map {
                it.outputDir.resolve(nativeBuildType.getName()).resolve("$projectName.xcframework")
            })

            into(outputDir)

            doFirst {
                logger.lifecycle("Cleaning existing XCFramework at: ${outputDir.absolutePath}")
                outputDir.deleteRecursively()

                if (extension.cleanIntermediate.get()) {
                    logger.lifecycle("Cleaning intermediates for $projectName")
                    intermediatesDir?.toPath()?.listDirectoryEntries("${extension.frameworkName.get()}*")?.forEach {
                        it.deleteRecursively()
                        logger.lifecycle("Cleaned intermediate files: ${it.fileName}")
                    }
                }
            }
        }
    }

    private fun Project.validateConfiguration() {
        if (!plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")) {
            throw GradleException("The Kotlin Multiplatform plugin must be applied before the XCFramework plugin")
        }

        if (!rootProject.file("ios").exists()) {
            throw GradleException("iOS directory not found. Expected at: ${rootProject.file("ios").absolutePath}")
        }
    }
}