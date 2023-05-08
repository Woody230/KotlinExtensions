plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Compose Multiplatform internationalization.")

kotlin.sourceSets.commonMain {
    api(libs.compose.ui)
    api(projects.intl)
}