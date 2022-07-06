plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Text extensions for Jetbrains Compose."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectComposeUiGraphics()
    }
    commonTest()
}