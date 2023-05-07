plugins {
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

publish(description = "moko-resources extensions")

android.setup(project) {
    setupMokoResources(project)
}

kotlin.setup {
    commonMain {
        api(libs.moko.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().forEach { task ->
    task.dependsOn("generateMRandroidMain")
    task.dependsOn("generateMRjvmMain")
}