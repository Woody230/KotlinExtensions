plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "Value class wrappers for identifiers."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
    }
}