plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "Image models."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktx.serialization.core)
    }
}