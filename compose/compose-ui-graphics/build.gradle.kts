import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Graphics extensions for Compose Multiplatform.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
        api(libs.compose.material)
    }
}