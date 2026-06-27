package io.github.woody230.ktx.convention

import com.bselzer.gradle.internal.android.kotlin.multiplatform.library.plugin.multiplatformAndroidLibraryExtension
import libs

plugins {
    // TODO https://github.com/radoslaw-panuszewski/typesafe-conventions-gradle-plugin/issues/82
    id("io.github.woody230.ktx.convention.multiplatform")

    alias(libs.plugins.ktx.serialization)
}

multiplatformAndroidLibraryExtension {
    namespace.module.set("serialization.$name")
}

multiplatformPublishExtension {
    coordinates.module.set("serialization-$name")
}