import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Common composable implementations for Compose Multiplatform.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.composeConstraintLayout)
        implementation(projects.function)
        api(projects.composeUiLayout)
    }
}