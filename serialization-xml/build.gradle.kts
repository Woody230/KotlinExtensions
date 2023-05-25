import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization extensions for pdvrieze.xmlutil")
}

multiplatformDependencies {
    commonMain {
        api(libs.xml.serialization)
        implementation(projects.logging)
    }
}