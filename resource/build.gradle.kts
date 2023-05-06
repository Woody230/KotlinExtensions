plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.moko.resources)
    id(libs.plugins.publish.get().pluginId)
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

// TODO temporarily explicitly declare dependency
tasks.withType<org.gradle.jvm.tasks.Jar> {
    dependsOn("generateMRandroidMain")
}

publish(description = "moko-resources extensions")

android.setup(project) {
    // TODO temporary srcDir inclusion https://github.com/icerockdev/moko-resources/issues/353
    sourceSets["main"].apply {
        assets.srcDir(File(buildDir, "generated/moko/androidMain/assets"))
        res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
    }
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.moko.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}