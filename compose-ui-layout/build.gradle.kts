plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Base extensions for laying out Compose Multiplatform UI."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.compose.material)
        api(libs.compose.foundation)
        api(projects.composeUi)
    }
}