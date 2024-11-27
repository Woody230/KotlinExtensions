import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.store.get().pluginId)
}

multiplatformPublishExtension {
    description.set("In memory store.")
}

multiplatformDependencies {
    commonMain {
        api(projects.core)
    }
}