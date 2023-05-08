plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Unit extensions for Compose Multiplatform.")

kotlin.sourceSets.commonMain {
    api(libs.compose.ui)
}