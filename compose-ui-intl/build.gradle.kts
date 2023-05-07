plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Compose Multiplatform internationalization."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.compose.ui)
        api(projects.intl)
    }
}