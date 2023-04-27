plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

// This module is a copy of https://github.com/androidx/constraintlayout with minor modifications to make it usable for multiplatform.
publishing.publish(
    project = project,
    description = "A copy of Android's ConstraintLayout (v2.1.3 core and v1.0.0 compose) with multiplatform capability."
) {
    developer {
        name.set("The Android Open Source Project")
    }
}

android.setupWithCompose {
    packagingOptions {
        resources.pickFirsts.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

kotlin.setup {
    commonMain {
        runtime()
        ui()
        uiUtil()
        material()
        foundation()
        ktxDateTime()
    }
    commonTest()
    androidUnitTest()
}