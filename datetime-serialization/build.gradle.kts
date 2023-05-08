import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("kotlinx.serialization for kotlinx.datetime")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.datetime)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}