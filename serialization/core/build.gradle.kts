import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.serialization.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization core extensions")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        implementation(libs.woody230.ktx.logging)
    }
    commonTest {
        implementation(projects.json)
        implementation(projects.xml)
    }
}