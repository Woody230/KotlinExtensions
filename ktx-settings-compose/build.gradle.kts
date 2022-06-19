plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(project)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        coroutine()
        projectSettings()
        projectSerializationCompose()
    }
    commonTest()
}