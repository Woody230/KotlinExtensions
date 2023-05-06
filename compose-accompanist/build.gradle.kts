plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    id(libs.plugins.vanniktech.publish.get().pluginId)
}

// This module is a copy of modules within Google's Accompanist project with minor modifications to make it usable for multiplatform.
// See https://github.com/google/accompanist/ and https://github.com/chrisbanes/snapper
publish(
    description = "A copy of Google's Accompanist (v0.23.1 and v0.2.1 Snapper) with multiplatform capability. Currently this only includes the pager and pager indicators."
) {
    developer {
        name.set("Google")
    }
    developer {
        name.set("Chris Banes")
    }
}

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.bundles.compose)
        implementation(libs.napier)
    }
}