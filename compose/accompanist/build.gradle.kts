import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

// This module is a copy of modules within Google's Accompanist project with minor modifications to make it usable for multiplatform.
// See https://github.com/google/accompanist/ and https://github.com/chrisbanes/snapper

multiplatformPublishExtension {
    description.set("A copy of Google's Accompanist (v0.23.1 and v0.2.1 Snapper) with multiplatform capability. Currently this only includes the pager and pager indicators.")
    developer {
        name.set("Google")
    }
    developer {
        name.set("Chris Banes")
    }
}

multiplatformDependencies {
    commonMain {
        api(libs.bundles.compose)
        implementation(libs.napier)
    }
}