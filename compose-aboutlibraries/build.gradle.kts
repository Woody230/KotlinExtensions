plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Composable implementation of AboutLibraries using Compose Multiplatform.")

kotlin.sourceSets.commonMain {
    api(projects.aboutlibraries)
    api(projects.composeResource)
    api(projects.composeUiLayoutCustom)
}