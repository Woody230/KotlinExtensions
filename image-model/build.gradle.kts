import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("Image models.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
    }
}
