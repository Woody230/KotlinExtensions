import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.test.get().pluginId)
    alias(libs.plugins.ktx.serialization)
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