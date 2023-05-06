plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.ktx.serialization)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "multiplatform-settings extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.serialization.json)
        api(libs.ktx.coroutines)
        api(libs.settings)
        api(libs.settings.coroutines)
        api(projects.valueIdentifier)
    }
}