import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.test.get().pluginId)
}

multiplatformPublishExtension {
    description.set("multiplatform-settings extensions for Compose Multiplatform classes")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.coroutines)
        api(projects.settings)
        api(projects.composeUiGraphics)
    }
}