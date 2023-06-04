import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.internal.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.internal.multiplatform.compose.test.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Custom composable implementations for Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(projects.composeUiLayoutCommon)
        implementation(libs.woody230.ktx.function)
    }
}