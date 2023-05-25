import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("multiplatform-settings extensions")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.json)
        api(libs.ktx.coroutines)
        api(libs.settings)
        api(libs.settings.coroutines)
        api(projects.valueIdentifier)
    }
}