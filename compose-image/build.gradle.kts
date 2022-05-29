plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version COMPOSE
    kotlin("plugin.serialization") version KOTLIN
}

publishing.publish(project)

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