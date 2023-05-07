plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Composable implementation of AboutLibraries using Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(projects.aboutlibraries)
        api(projects.composeResource)
        api(projects.composeUiLayoutCustom)
    }
}