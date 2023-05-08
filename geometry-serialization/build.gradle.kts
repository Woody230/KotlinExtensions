plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("kotlinx.serialization for two and three dimensional geometrical objects.")

kotlin.sourceSets.apply {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.geometry)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}