plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.vanniktech.publish.get().pluginId)
}

publish(
    description = "Kodein-DB extensions."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.kodein.db)
        api(projects.valueIdentifier)
    }
}