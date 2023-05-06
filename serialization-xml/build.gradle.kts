plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.ktx.serialization)
    id(libs.plugins.vanniktech.publish.get().pluginId)
}

publish(
    description = "kotlinx.serialization extensions for pdvrieze.xmlutil"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.xml.serialization)
        implementation(projects.logging)
    }
    commonTest {
        implementation(libs.bundles.common.test)
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
    }
}