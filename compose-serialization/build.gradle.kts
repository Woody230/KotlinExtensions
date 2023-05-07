plugins {
    alias(libs.plugins.compose)
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "kotlinx.serialization extension for Compose Multiplatform classes"
)

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)
}

kotlin.setup {
    commonMain {
        api(libs.ktx.serialization.core)
        api(projects.composeUiGraphics)
    }
    commonTest {
        implementation(libs.ktx.serialization.json)
    }
}