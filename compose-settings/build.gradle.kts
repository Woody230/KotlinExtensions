plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "multiplatform-settings extensions for Compose Multiplatform classes"
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.ktx.coroutines)
        api(projects.settings)
        api(projects.composeUiGraphics)
    }
}