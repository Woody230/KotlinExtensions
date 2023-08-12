import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Wrappers for strings and images using the compose and resource modules.")
}

multiplatformDependencies {
    commonMain {
        api(libs.woody230.ktx.resource)
        api(projects.uiLayoutCommon)
        api(projects.uiIntl)
    }
}