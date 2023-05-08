import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("Value class wrappers for enumerations.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.serialization)
    }
}