plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Base extensions for laying out Compose Multiplatform UI.")

kotlin.sourceSets.commonMain {
    api(libs.compose.material)
    api(libs.compose.foundation)
    api(projects.composeUi)
}