plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
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