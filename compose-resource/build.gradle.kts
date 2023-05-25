import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.multiplatform.compose.asProvider().get().pluginId)
}

multiplatformPublishExtension {
    description.set("Wrappers for strings and images using the compose and resource modules.")
}

multiplatformDependencies {
    commonMain {
        api(projects.resource)
        api(projects.composeUiLayoutCommon)
        api(projects.composeUiIntl)
    }
}