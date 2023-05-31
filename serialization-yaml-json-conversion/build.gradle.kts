import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extensions for converting a YamlElement to a JsonElement and vice versa")
}

multiplatformDependencies {
    commonMain {
        api(projects.serializationJson)
        api(projects.serializationYaml)
    }
}