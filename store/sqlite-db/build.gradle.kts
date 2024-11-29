plugins {
    id(libs.plugins.woody230.ktx.convention.store.get().pluginId)
    id(libs.plugins.sqldelight.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Internal database implementation for the sqlite module using SqlDelight.")
}

sqldelight {
    databases {
        create("DataStoreDatabase") {
            packageName = "com.bselzer.ktx.store.sqlite.db"
        }
    }
}