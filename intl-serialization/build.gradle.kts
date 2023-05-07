plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "kotlinx.serialization for internationalization"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.intl)
    }
}