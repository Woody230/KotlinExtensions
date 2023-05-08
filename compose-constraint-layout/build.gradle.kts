plugins {
    alias(libs.plugins.compose)
}

// This module is a copy of https://github.com/androidx/constraintlayout with minor modifications to make it usable for multiplatform.
publishConvention.apply {
    description.set("A copy of Android's ConstraintLayout (v2.1.3 core and v1.0.0 compose) with multiplatform capability.")
    devs.set {
        developer {
            name.set("The Android Open Source Project")
        }
    }
}

kotlin.sourceSets.commonMain {
    api(libs.bundles.compose)
    api(libs.ktx.datetime)
}