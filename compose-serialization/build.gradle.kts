plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
    alias(libs.plugins.ktx.serialization)
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization extension for Compose Multiplatform classes"
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.serialization.core)
        api(projects.composeUiGraphics)
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