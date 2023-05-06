plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.ktx.serialization)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "kotlinx.serialization for internationalization"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.serialization.core)
        api(projects.intl)
    }
}