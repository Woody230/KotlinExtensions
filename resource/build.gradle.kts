plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.moko.resources)
    id(libs.plugins.vanniktech.publish.get().pluginId)
}

buildscript {
    dependencies {
        classpath(libs.moko.resources.generator)
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.bselzer.ktx.resource"
    multiplatformResourcesClassName = "KtxResources"
}

publish(description = "moko-resources extensions")

android.setup(project) {
    setupMokoResources(project)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.moko.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}