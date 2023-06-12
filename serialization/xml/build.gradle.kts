import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.serialization.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extensions for pdvrieze.xmlutil")
}

multiplatformDependencies {
    commonMain {
        api(projects.core)
        api(libs.xml.serialization)
        implementation(libs.woody230.ktx.logging)
    }
}