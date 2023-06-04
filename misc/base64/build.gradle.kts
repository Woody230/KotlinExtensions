import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Extensions for base64 encoding and decoding between strings and byte arrays.")
    developer {
        name.set("jershell")
    }
}

multiplatformDependencies {
    commonTest {
        implementation(projects.function)
    }
}