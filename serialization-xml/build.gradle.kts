import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("kotlinx.serialization extensions for pdvrieze.xmlutil")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.xml.serialization)
        implementation(projects.logging)
    }
}