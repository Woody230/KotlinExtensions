plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "General Kotlin standard library extensions."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
    }
    androidMain {
        api(libs.androidx.core.ktx)
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