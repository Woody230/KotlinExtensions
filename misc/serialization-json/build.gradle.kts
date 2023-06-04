import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization json extensions")
}

multiplatformDependencies {
    commonMain {
        api(projects.serializationCore)
        api(libs.ktx.serialization.json)
    }
    commonTest {
        implementation(projects.serializationXml)
    }
}