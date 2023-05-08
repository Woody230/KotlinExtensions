plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("multiplatform-settings extensions")

kotlin.sourceSets.commonMain {
    api(libs.ktx.serialization.json)
    api(libs.ktx.coroutines)
    api(libs.settings)
    api(libs.settings.coroutines)
    api(projects.valueIdentifier)
}