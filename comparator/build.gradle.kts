import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Object comparators.")
}

multiplatformDependencies {
    commonMain {
        implementation(projects.function)
    }
}