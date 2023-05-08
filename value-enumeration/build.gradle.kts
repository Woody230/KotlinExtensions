plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("Value class wrappers for enumerations.")

kotlin.sourceSets.commonMain {
    api(projects.serialization)
}