import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Fetching image content via Ktor.")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktor.client)
        implementation(projects.logging)
        api(projects.imageModel)
    }
}