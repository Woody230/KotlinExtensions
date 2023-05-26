import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.internal.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.internal.multiplatform.compose.test.get().pluginId)
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