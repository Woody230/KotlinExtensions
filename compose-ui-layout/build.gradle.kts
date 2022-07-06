plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Composable implementations for Jetbrains Compose."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        material()
        foundation()
        projectComposeUi()
    }
    commonTest()
    androidTest()
    jvmTest()
}