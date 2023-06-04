import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Composable implementation of AboutLibraries using Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(libs.woody230.ktx.aboutlibraries)
        api(projects.composeResource)
        api(projects.composeUiLayoutCustom)
    }
}