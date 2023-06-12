import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Based on Android intents.")
}

multiplatformDependencies {
    commonMain {
        implementation(libs.woody230.ktx.logging)
    }
}