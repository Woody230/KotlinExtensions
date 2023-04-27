plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Extensions for Compose Multiplatform UI."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        ui()
    }
    commonTest()
}