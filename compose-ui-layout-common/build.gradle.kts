import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
}

multiplatformPublishExtension {
    description.set("Common composable implementations for Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(projects.composeConstraintLayout)
        implementation(projects.function)
        api(projects.composeUiLayout)
    }
}