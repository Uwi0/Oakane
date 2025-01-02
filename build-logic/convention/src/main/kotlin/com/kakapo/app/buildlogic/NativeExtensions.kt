package com.kakapo.app.buildlogic

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.konan.target.KonanTarget
import java.io.File
import java.util.Locale

enum class NativeTargetType(
    val targets: List<KonanTarget>,
    private val platformName: String,
) {
    DEVICE(listOf(KonanTarget.IOS_ARM64), "iphoneos"),
    SIMULATOR(listOf(KonanTarget.IOS_SIMULATOR_ARM64), "iphonesimulator");

    val capitalizedName: String = name.lowercase(Locale.ENGLISH).replaceFirstChar { it.uppercase() }

    companion object {
        fun from(platformName: String?): NativeTargetType = values()
            .firstOrNull { it.platformName == platformName?.lowercase() } ?: SIMULATOR
    }
}

internal object XcodeBuildEnvironment {
    const val GROUP_NAME = "oakane"

    val nativeBuildType: NativeBuildType
        get() = when (System.getenv("CONFIGURATION")?.lowercase()) {
            "release" -> NativeBuildType.RELEASE
            else -> NativeBuildType.DEBUG
        }

    val nativeTargetType: NativeTargetType
        get() = NativeTargetType.from(System.getenv("RUN_DESTINATION_DEVICE_PLATFORM_NAME"))

    val intermediatesDir: File?
        get() = try {
            System.getenv("OBJROOT")?.let { File(it)}
        } catch (e: NoSuchFileException) {
            null
        }

    val nativeBuildTargetTypes: List<Pair<NativeBuildType, NativeTargetType>>
        get() = NativeBuildType.values().flatMap { buildType ->
            NativeTargetType.values().map { targetType ->
                buildType to targetType
            }
        }

    val NativeBuildType.capitalizedName: String
        get() = getName().replaceFirstChar { it.uppercase() }
}

internal fun KotlinMultiplatformExtension.nativeFrameworks(
    buildType: NativeBuildType,
    targets: List<KonanTarget>,
): List<Framework> = this.targets
    .filterIsInstance<KotlinNativeTarget>()
    .filter { targets.contains(it.konanTarget) }
    .flatMap { it.binaries.filterIsInstance<Framework>() }
    .filter { it.buildType == buildType }
