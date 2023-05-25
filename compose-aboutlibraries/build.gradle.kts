import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.test.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Composable implementation of AboutLibraries using Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(projects.aboutlibraries)
        api(projects.composeResource)
        api(projects.composeUiLayoutCustom)
    }
}