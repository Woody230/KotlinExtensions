plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "Jetbrains Compose image fetching and displaying."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        ktorClient()
        ktxSerialization()
        projectCompose()
        projectKodeinDb()
        projectLogging()
    }
    commonTest()
}