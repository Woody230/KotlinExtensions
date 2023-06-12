import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("Value class wrappers for enumerations.")
}

multiplatformDependencies {
    commonMain {
        api(libs.woody230.ktx.serialization.json)
    }
}