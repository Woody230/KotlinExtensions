import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Wrappers for strings and images using the compose and resource modules.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.resource)
        api(projects.composeUiLayoutCommon)
        api(projects.composeUiIntl)
    }
}