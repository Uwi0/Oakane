package com.kakapo.app.buildlogic

import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor
import org.gradle.api.Project

const val NAME = "Oakane"

@Suppress("EnumEntryName")
enum class FlavorDimension {
    contentType
}

@Suppress("EnumEntryName")
enum class OakaneFlavor(
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null
) {
    demo(
        dimension = FlavorDimension.contentType,
        applicationIdSuffix = "demo"
    ),
    prod(
        dimension = FlavorDimension.contentType
    )
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    flavorConfigurationBlock: ProductFlavor.(flavor: OakaneFlavor) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.contentType.name
        productFlavors {
            OakaneFlavor.values().forEach { flavor ->
                create(flavor.name) {
                    dimension = flavor.dimension.name
                    flavorConfigurationBlock(this, flavor)
                    resValue("string", "app_name", flavor.applicationIdSuffix?.let { "$NAME-$it" } ?: NAME)
                    if (this is ApplicationProductFlavor) {
                        flavor.applicationIdSuffix?.let { this.applicationIdSuffix = it }
                    }
                }
            }
        }
    }
}