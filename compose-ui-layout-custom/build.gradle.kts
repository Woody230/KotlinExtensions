plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Custom composable implementations for Compose Multiplatform.")

kotlin.sourceSets.commonMain {
    api(projects.composeUiLayoutCommon)
    implementation(projects.function)
}