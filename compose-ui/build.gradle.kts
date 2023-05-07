plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Extensions for Compose Multiplatform UI."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.compose.ui)
    }
}