import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("coroutine extensions")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.coroutines)
    }
}