plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("multiplatform-settings extensions for Compose Multiplatform classes")

kotlin.sourceSets.commonMain {
    api(libs.ktx.coroutines)
    api(projects.settings)
    api(projects.composeUiGraphics)
}