import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    alias(libs.plugins.compose)
}

publishConvention {
    description.set("Compose Multiplatform internationalization.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
        api(projects.intl)
    }
}