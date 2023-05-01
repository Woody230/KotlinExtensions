plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "AboutLibraries extensions."
)

android.setup(project)

kotlin.setup {
    commonMain {
        aboutLibraries()
    }
    commonTest()
}