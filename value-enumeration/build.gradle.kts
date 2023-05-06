plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.ktx.serialization)
    id(libs.plugins.vanniktech.publish.get().pluginId)
    alias(libs.plugins.dokka)
}

publish(
    description = "Value class wrappers for enumerations."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(projects.serialization)
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