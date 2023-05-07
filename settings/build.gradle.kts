plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "multiplatform-settings extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktx.serialization.json)
        api(libs.ktx.coroutines)
        api(libs.settings)
        api(libs.settings.coroutines)
        api(projects.valueIdentifier)
    }
}