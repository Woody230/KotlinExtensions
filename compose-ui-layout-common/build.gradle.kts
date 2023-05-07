plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Common composable implementations for Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(projects.composeConstraintLayout)
        implementation(projects.function)
        api(projects.composeUiLayout)
    }
}