plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.ktx.serialization)
    id(libs.plugins.vanniktech.publish.get().pluginId)
    alias(libs.plugins.dokka)
}

publish(
    description = "kotlinx.serialization extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)

        // TODO move JsonContext to a serialization-json and use core instead
        api(libs.ktx.serialization.json)

        implementation(projects.logging)
    }
    commonTest {
        implementation(libs.bundles.common.test)
        implementation(libs.xml.serialization)
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
    }
}