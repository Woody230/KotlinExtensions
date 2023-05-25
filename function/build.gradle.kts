import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("General Kotlin standard library extensions.")
}

multiplatformDependencies {
    androidMain {
        api(libs.androidx.core.ktx)
    }
}