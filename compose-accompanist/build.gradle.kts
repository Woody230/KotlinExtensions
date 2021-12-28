plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version COMPOSE
}

// This module is a copy of modules within Google's Accompanist project with minor modifications to make it usable for multiplatform.
// See https://github.com/google/accompanist/ and https://github.com/chrisbanes/snapper
publishing.publish(project) {
    developer {
        name.set("Google")
    }
    developer {
        name.set("Chris Banes")
    }
}

android.setupWithCompose()

kotlin.setup {
    commonMain {
        runtime()
        ui()
        foundation()
        logging()
    }
    commonTest()
}