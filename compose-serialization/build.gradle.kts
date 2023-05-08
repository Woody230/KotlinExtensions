import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("kotlinx.serialization extension for Compose Multiplatform classes")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.composeUiGraphics)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}