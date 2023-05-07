plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Unit extensions for Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.compose.ui)
    }
}