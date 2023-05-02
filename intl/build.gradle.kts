plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "Internationalization."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
    }
}