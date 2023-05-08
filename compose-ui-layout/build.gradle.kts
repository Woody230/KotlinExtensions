import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Base extensions for laying out Compose Multiplatform UI.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.compose.material)
        api(libs.compose.foundation)
        api(projects.composeUi)
    }
}