import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.internal.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.internal.multiplatform.compose.test.get().pluginId)
}

// This module is a copy of https://github.com/androidx/constraintlayout with minor modifications to make it usable for multiplatform.
multiplatformPublishExtension {
    description.set("A copy of Android's ConstraintLayout (v2.1.3 core and v1.0.0 compose) with multiplatform capability.")
    developer {
        name.set("The Android Open Source Project")
    }
}

multiplatformDependencies {
    commonMain {
        api(libs.bundles.compose)
        api(libs.ktx.datetime)
    }
}