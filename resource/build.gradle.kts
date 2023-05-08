plugins {
    id(libs.plugins.woody230.moko.get().pluginId)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.bselzer.ktx.resource"
    multiplatformResourcesClassName = "KtxResources"
}

publishConvention.description.set("moko-resources extensions")

kotlin.sourceSets.commonMain {
    api(libs.moko.resources)
    implementation(projects.intent)
    api(projects.intl)
}