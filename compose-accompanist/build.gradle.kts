plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

// This module is a copy of modules within Google's Accompanist project with minor modifications to make it usable for multiplatform.
// See https://github.com/google/accompanist/ and https://github.com/chrisbanes/snapper
publishing.publish(
    project = project,
    description = "A copy of Google's Accompanist (v0.23.1 and v0.2.1 Snapper) with multiplatform capability. Currently this only includes the pager and pager indicators."
) {
    developer {
        name.set("Google")
    }
    developer {
        name.set("Chris Banes")
    }
}

android.setupWithCompose(project)

kotlin.setup {
    commonMain {
        runtime()
        ui()
        material()
        foundation()
        napier()
    }
    commonTest()
}