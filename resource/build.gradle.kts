plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.moko.resources)
    id(libs.plugins.vanniktech.publish.get().pluginId)
    alias(libs.plugins.dokka)
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

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().forEach { task ->
    task.dependsOn("generateMRandroidMain")
    task.dependsOn("generateMRjvmMain")
}