import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.moko.resources.get().pluginId)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.bselzer.ktx.resource"
    multiplatformResourcesClassName = "KtxResources"
}

multiplatformPublishExtension {
    description.set("moko-resources extensions")
}

multiplatformDependencies {
    commonMain {
        api(libs.moko.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}