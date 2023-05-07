plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "kotlinx.serialization for two and three dimensional geometrical objects."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.geometry)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}