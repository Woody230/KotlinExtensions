plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.vanniktech.publish.get().pluginId)
    alias(libs.plugins.dokka)
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