package io.github.woody230.ktx.convention

import com.bselzer.gradle.internal.android.kotlin.multiplatform.library.plugin.multiplatformAndroidLibraryExtension

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("io.github.woody230.ktx.convention.multiplatform")
    id("io.github.woody230.gradle.internal.multiplatform-compose")
    id("io.github.woody230.gradle.internal.multiplatform-compose-test")
}

multiplatformAndroidLibraryExtension {
    namespace.module.set("compose.$name")
}

multiplatformPublishExtension {
    coordinates.module.set("compose-$name")
}