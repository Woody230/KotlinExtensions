plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "Value class wrappers for identifiers."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
    }
}