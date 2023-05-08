import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Extensions for Compose Multiplatform UI.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
    }
}