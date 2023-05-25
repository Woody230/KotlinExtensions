import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Logging wrapper around Napier.")
}

multiplatformDependencies {
    commonMain {
        implementation(libs.napier)
    }
}