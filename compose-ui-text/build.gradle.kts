import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Text extensions for Compose Multiplatform.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.composeUiGraphics)
    }
}