plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "multiplatform-settings extensions for Jetbrains Compose classes"
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        coroutine()
        projectSettings()
        projectSerializationCompose()
    }
    commonTest()
}