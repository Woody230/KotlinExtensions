import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Kotlin Multiplatform resource extensions")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}