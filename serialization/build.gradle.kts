plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "kotlinx.serialization extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        // TODO move JsonContext to a serialization-json and use core instead
        api(libs.ktx.serialization.json)

        implementation(projects.logging)
    }
    commonTest {
        implementation(libs.xml.serialization)
    }
}