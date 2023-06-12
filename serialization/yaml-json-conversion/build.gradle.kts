import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.serialization.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extensions for converting a YamlElement to a JsonElement and vice versa")
}

multiplatformDependencies {
    commonMain {
        api(projects.json)
        api(projects.yaml)
    }
}