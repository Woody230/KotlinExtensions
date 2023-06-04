import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
    id(libs.plugins.ktx.serialization.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extension for Compose Multiplatform classes")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.composeUiGraphics)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}