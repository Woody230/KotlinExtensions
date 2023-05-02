plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
}

publishing.publish(
    project = project,
    description = "multiplatform-settings extensions for Compose Multiplatform classes"
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.coroutines)
        projectSettings()
        projectComposeUiGraphics()
    }
}