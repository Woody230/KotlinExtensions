import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extensions")
}

multiplatformDependencies {
    commonMain {
        // TODO move JsonContext to a serialization-json and use core instead
        api(libs.ktx.serialization.json)

        implementation(projects.logging)
    }
    commonTest {
        implementation(libs.xml.serialization)
    }
}