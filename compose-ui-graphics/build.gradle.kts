plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Graphics extensions for Compose Multiplatform.")

kotlin.sourceSets.commonMain {
    api(libs.compose.ui)
    api(libs.compose.material)
}