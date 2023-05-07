plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Graphics extensions for Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.compose.ui)
        api(libs.compose.material)
    }
}