import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
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