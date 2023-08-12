import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.compose.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Compose Multiplatform internationalization.")
}

multiplatformDependencies {
    commonMain {
        api(libs.compose.ui)
        api(libs.woody230.ktx.intl)
    }
}