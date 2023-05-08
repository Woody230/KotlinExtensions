plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("kotlinx.serialization for internationalization")

kotlin.sourceSets.commonMain {
    api(libs.ktx.serialization.core)
    api(projects.intl)
}