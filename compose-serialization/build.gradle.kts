plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization extension for Compose Multiplatform classes"
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectComposeUiGraphics()
        ktxSerialization()
    }
    commonTest()
    androidUnitTest()
    jvmTest()
}