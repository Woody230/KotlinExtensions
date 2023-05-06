plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    id(libs.plugins.vanniktech.publish.get().pluginId)
    alias(libs.plugins.dokka)
}

publish(
    description = "Wrappers for strings and images using the compose and resource modules."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(projects.resource)
        api(projects.composeUiLayoutCommon)
        api(projects.composeUiIntl)
    }
}