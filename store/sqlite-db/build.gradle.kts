import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.store.get().pluginId)
    id(libs.plugins.sqldelight.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Internal database implementation for the sqlite module using SqlDelight.")
}

multiplatformDependencies {
    androidMain {
        implementation(libs.sqldelight.android)
    }
    jvmMain {
        implementation(libs.sqldelight.jvm)
    }
}

sqldelight {
    databases {
        create("DataStoreDatabase") {
            packageName = "com.bselzer.ktx.store.sqlite"
        }
    }
}