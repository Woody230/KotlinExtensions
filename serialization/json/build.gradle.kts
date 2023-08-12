import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.serialization.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization json extensions")
}

multiplatformDependencies {
    commonMain {
        api(projects.core)
        api(libs.ktx.serialization.json)
    }
    commonTest {
        implementation(projects.xml)
    }
}