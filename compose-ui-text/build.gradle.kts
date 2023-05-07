plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Text extensions for Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(projects.composeUiGraphics)
    }
}