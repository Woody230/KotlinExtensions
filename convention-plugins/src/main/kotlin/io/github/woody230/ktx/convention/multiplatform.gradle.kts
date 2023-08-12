package io.github.woody230.ktx.convention

import com.bselzer.gradle.internal.android.library.plugin.androidLibraryExtension
import com.bselzer.gradle.internal.maven.publish.plugin.Licensing
import com.bselzer.gradle.internal.multiplatform.publish.plugin.multiplatformPublishExtension
import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies
import org.gradle.kotlin.dsl.apply

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("io.github.woody230.gradle.internal.android-library")
    id("io.github.woody230.gradle.internal.multiplatform")
    id("io.github.woody230.gradle.internal.multiplatform-test")
    id("io.github.woody230.gradle.internal.multiplatform-publish")
}

androidLibraryExtension {
    namespace.category.set("ktx")
}

multiplatformPublishExtension {
    coordinates.category.set("ktx")
    version.set(libs.versions.woody230.ktx)
    repository.set("https://github.com/Woody230/KotlinExtensions")
    licensing.set(Licensing.APACHE_2_0)
}

multiplatformDependencies {
    commonMain {
        api(libs.kotlin.stdlib)
    }
}