plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "Logging wrapper around Napier."
)

android.setup(project)

kotlin.setup {
    commonMain {
        implementation(libs.napier)
    }
}