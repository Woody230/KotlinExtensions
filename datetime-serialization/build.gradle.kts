plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "kotlinx.serialization for kotlinx.datetime"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.datetime)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}