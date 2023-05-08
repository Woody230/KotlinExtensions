import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Custom composable implementations for Compose Multiplatform.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.composeUiLayoutCommon)
        implementation(projects.function)
    }
}