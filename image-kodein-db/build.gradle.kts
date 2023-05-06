plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "Storing image content via Kodein-DB."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(projects.kodeinDb)
        api(projects.imageModel)
    }
}