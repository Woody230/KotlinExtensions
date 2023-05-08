plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Text extensions for Compose Multiplatform.")

kotlin.sourceSets.commonMain {
    api(projects.composeUiGraphics)
}