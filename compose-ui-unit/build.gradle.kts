plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Unit extensions for Jetbrains Compose."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        ui()
    }
    commonTest()
}