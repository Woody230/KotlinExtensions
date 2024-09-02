import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

compose.resources {
    generateResClass = always

    // TODO enable when custom naming allowed https://youtrack.jetbrains.com/issue/CMP-4763/Compose-resources-Allow-custom-naming-of-Res-class-generated
    publicResClass = false

    packageOfResClass = "com.bselzer.ktx.compose.resource"
}

multiplatformPublishExtension {
    description.set("Common resources for strings and images.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.resources)
        api(projects.uiLayoutCommon)
        api(projects.uiIntl)
    }
}