plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "Extensions for base64 encoding and decoding between strings and byte arrays."
) {
    developer {
        name.set("jershell")
    }
}

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
    }
    commonTest {
        implementation(libs.bundles.common.test)
        implementation(projects.function)
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
    }
}