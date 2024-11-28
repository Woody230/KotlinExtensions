import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.store.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Core logic for storing models.")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.coroutines)
    }
}