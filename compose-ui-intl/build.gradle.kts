import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.test.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Compose Multiplatform internationalization.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
        api(projects.intl)
    }
}