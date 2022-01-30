plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version COMPOSE
}

// This module is a copy of https://github.com/androidx/constraintlayout with minor modifications to make it usable for multiplatform.
publishing.publish(project) {
    developer {
        name.set("The Android Open Source Project")
    }
}

android.setupWithCompose {
    packagingOptions {
        //resources.pickFirsts.add("META-INF/*")
    }
}

kotlin.setup {
    commonMain {
        runtime()
        ui()
        material()
        foundation()
    }
    commonTest()
    androidTestWithCompose()
}