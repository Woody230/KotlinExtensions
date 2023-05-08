import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    id(libs.plugins.woody230.moko.get().pluginId)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.bselzer.ktx.resource"
    multiplatformResourcesClassName = "KtxResources"
}

publishConvention {
    description.set("moko-resources extensions")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.moko.resources)
        implementation(projects.intent)
        api(projects.intl)
    }
}