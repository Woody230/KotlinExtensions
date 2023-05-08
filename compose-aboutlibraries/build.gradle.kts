import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Composable implementation of AboutLibraries using Compose Multiplatform.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.aboutlibraries)
        api(projects.composeResource)
        api(projects.composeUiLayoutCustom)
    }
}