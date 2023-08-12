import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Extensions for Compose Multiplatform UI.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
    }
}