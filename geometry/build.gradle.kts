plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "Two and three dimensional geometrical objects."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
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