package io.github.woody230.ktx.convention

import com.bselzer.gradle.internal.android.kotlin.multiplatform.library.plugin.multiplatformAndroidLibraryExtension
import libs

plugins {
    // TODO https://github.com/radoslaw-panuszewski/typesafe-conventions-gradle-plugin/issues/82
    id("io.github.woody230.ktx.convention.multiplatform")

    alias(libs.plugins.woody230.gradle.internal.multiplatform.compose)
    alias(libs.plugins.woody230.gradle.internal.multiplatform.compose.test)
}

multiplatformAndroidLibraryExtension {
    namespace.module.set("compose.$name")
}

multiplatformPublishExtension {
    coordinates.module.set("compose-$name")
}