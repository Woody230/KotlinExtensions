plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.ktx.serialization)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "kotlinx.serialization for two and three dimensional geometrical objects."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.serialization.core)
        api(projects.geometry)
    }
    commonTest {
        implementation(libs.bundles.common.test)
        implementation(libs.ktx.serialization.json)
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
    }
}