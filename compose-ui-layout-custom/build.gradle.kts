plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Custom composable implementations for Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(projects.composeUiLayoutCommon)
        implementation(projects.function)
    }
}