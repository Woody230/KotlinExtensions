import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("multiplatform-settings extensions for Compose Multiplatform classes")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.coroutines)
        api(projects.settings)
        api(projects.composeUiGraphics)
    }
}