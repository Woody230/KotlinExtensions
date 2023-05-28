import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization core extensions")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        implementation(projects.logging)
    }
    commonTest {
        implementation(projects.serializationJson)
        implementation(projects.serializationXml)
    }
}