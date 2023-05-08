import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("kotlinx.serialization extensions")
}

kotlinMultiplatformDependencies {
    commonMain {
        // TODO move JsonContext to a serialization-json and use core instead
        api(libs.ktx.serialization.json)

        implementation(projects.logging)
    }
    commonTest {
        implementation(libs.xml.serialization)
    }
}