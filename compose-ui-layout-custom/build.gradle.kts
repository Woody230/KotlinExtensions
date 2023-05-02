plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
}

publishing.publish(
    project = project,
    description = "Custom composable implementations for Compose Multiplatform."
)

android.setupWithCompose(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        projectComposeUiLayoutCommon()
    }
}