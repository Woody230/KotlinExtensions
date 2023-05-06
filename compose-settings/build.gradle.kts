plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "multiplatform-settings extensions for Compose Multiplatform classes"
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.coroutines)
        api(projects.settings)
        api(projects.composeUiGraphics)
    }
}