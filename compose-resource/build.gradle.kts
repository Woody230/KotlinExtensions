plugins {
    alias(libs.plugins.compose)
}

publish(
    description = "Wrappers for strings and images using the compose and resource modules."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(projects.resource)
        api(projects.composeUiLayoutCommon)
        api(projects.composeUiIntl)
    }
}