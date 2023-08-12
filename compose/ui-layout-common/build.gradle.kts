import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Common composable implementations for Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(projects.constraintLayout)
        implementation(libs.woody230.ktx.function)
        api(projects.uiLayout)
    }
}