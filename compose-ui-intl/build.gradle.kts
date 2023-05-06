plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    id(libs.plugins.vanniktech.publish.get().pluginId)
}

publish(
    description = "Compose Multiplatform internationalization."
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.compose.ui)
        api(projects.intl)
    }
}