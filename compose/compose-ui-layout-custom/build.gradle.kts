import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
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