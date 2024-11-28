import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.store.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Data store using SQLite.")
}

multiplatformDependencies {
    commonMain {
        api(projects.core)

        // https://github.com/sqldelight/sqldelight/issues/1333
        // Can't change visibility of the generated code, so it must be in a separate module.
        implementation(projects.sqliteDb)
    }
}