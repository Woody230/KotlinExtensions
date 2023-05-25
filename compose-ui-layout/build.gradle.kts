import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
}

multiplatformPublishExtension {
    description.set("Base extensions for laying out Compose Multiplatform UI.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.material)
        api(libs.compose.foundation)
        api(projects.composeUi)
    }
}