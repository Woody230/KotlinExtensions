import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
}

multiplatformPublishExtension {
    description.set("Text extensions for Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(projects.composeUiGraphics)
    }
}