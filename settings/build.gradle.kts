import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention {
    description.set("multiplatform-settings extensions")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.json)
        api(libs.ktx.coroutines)
        api(libs.settings)
        api(libs.settings.coroutines)
        api(projects.valueIdentifier)
    }
}