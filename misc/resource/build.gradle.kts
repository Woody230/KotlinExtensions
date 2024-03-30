import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    id(libs.plugins.woody230.gradle.internal.moko.resources.get().pluginId)
}

multiplatformResources {
    resourcesPackage = "com.bselzer.ktx.resource"
    resourcesClassName = "KtxResources"
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