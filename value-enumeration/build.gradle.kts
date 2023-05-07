plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "Value class wrappers for enumerations."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(projects.serialization)
    }
}