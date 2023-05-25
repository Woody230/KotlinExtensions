import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Kodein-DB extensions.")
}

multiplatformDependencies {
    commonMain {
        api(libs.kodein.db)
        api(projects.valueIdentifier)
    }
}