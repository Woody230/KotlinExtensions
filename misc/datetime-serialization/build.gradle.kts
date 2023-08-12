import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization for kotlinx.datetime")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.datetime)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}