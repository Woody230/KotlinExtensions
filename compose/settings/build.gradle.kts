import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("multiplatform-settings extensions for Compose Multiplatform classes")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.coroutines)
        api(libs.woody230.ktx.settings)
        api(projects.uiGraphics)
    }
}