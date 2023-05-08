plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("kotlinx.serialization extensions")

kotlin.sourceSets.apply {
    commonMain {
        // TODO move JsonContext to a serialization-json and use core instead
        api(libs.ktx.serialization.json)

        implementation(projects.logging)
    }
    commonTest {
        implementation(libs.xml.serialization)
    }
}