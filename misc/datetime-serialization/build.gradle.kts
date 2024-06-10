import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
    alias(libs.plugins.ktx.serialization)

    // datetime project needs desugaring
    id(libs.plugins.woody230.gradle.internal.android.desugar.get().pluginId)
}

multiplatformPublishExtension {
    description.set("kotlinx.serialization for kotlinx.datetime")
}

multiplatformDependencies {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.datetime)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}