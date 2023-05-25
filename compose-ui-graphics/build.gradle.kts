import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
}

multiplatformPublishExtension {
    description.set("Graphics extensions for Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
        api(libs.compose.material)
    }
}