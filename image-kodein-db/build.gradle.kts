plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "Storing image content via Kodein-DB."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        projectKodeinDb()
        projectImageModel()
    }
}