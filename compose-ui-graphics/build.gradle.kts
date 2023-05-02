plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
}

publishing.publish(
    project = project,
    description = "Graphics extensions for Compose Multiplatform."
)

android.setupWithCompose(project)

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.compose.ui)
        api(libs.compose.material)
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