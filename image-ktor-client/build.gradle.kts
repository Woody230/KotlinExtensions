plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.vanniktech.publish.get().pluginId)
}

publish(
    description = "Fetching image content via Ktor."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktor.client)
        implementation(projects.logging)
        api(projects.imageModel)
    }
}