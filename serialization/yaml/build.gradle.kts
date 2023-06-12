import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.serialization.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extensions for Him188's yamlkt")
}

multiplatformDependencies {
    commonMain {
        api(projects.core)
        api(libs.yaml.serialization)
    }
}