plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("kotlinx.serialization for kotlinx.datetime")

kotlin.sourceSets.apply {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.datetime)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}