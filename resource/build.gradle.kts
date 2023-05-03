plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.moko.resources)
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

publishing.publish(
    project = project,
    description = "moko-resources extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.moko.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}