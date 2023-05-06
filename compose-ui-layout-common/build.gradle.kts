plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "Common composable implementations for Compose Multiplatform."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(projects.composeConstraintLayout)
        implementation(projects.function)
        api(projects.composeUiLayout)
    }
}