plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Extensions for Compose Multiplatform UI.")

kotlin.sourceSets.commonMain {
    api(libs.compose.ui)
}