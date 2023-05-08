plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Common composable implementations for Compose Multiplatform.")

kotlin.sourceSets.commonMain {
    api(projects.composeConstraintLayout)
    implementation(projects.function)
    api(projects.composeUiLayout)
}