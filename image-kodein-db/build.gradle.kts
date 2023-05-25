import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Storing image content via Kodein-DB.")
}

multiplatformDependencies {
    commonMain {
        api(projects.kodeinDb)
        api(projects.imageModel)
    }
}