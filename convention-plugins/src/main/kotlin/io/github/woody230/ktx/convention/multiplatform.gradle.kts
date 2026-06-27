package io.github.woody230.ktx.convention

import com.bselzer.gradle.internal.android.kotlin.multiplatform.library.plugin.multiplatformAndroidLibraryExtension
import com.bselzer.gradle.internal.maven.publish.plugin.Licensing
import com.bselzer.gradle.internal.multiplatform.publish.plugin.multiplatformPublishExtension
import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies
import libs

plugins {
    alias(libs.plugins.woody230.gradle.internal.multiplatform.android.library)
    alias(libs.plugins.woody230.gradle.internal.multiplatform)
    alias(libs.plugins.woody230.gradle.internal.multiplatform.jvm.target)
    alias(libs.plugins.woody230.gradle.internal.multiplatform.test)
    alias(libs.plugins.woody230.gradle.internal.multiplatform.publish)
}

multiplatformAndroidLibraryExtension {
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