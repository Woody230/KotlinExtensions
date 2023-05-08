import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("kotlinx.serialization for two and three dimensional geometrical objects.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.geometry)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}