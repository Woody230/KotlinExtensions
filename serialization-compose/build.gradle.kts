plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version COMPOSE
    kotlin("plugin.serialization") version KOTLIN
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