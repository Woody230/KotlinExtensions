plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
}

publishing.publish(
    project = project,
    description = "Wrappers for strings and images using the compose and resource modules."
)

android.setupWithCompose(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        projectResource()
        projectComposeUiLayoutCommon()
        projectComposeUiIntl()
    }
}