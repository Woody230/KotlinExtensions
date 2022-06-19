plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(project)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectCompose()
        ktxSerialization()
    }
    commonTest()
    jvmTest()
}