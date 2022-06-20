plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Jetbrains Compose extensions for AboutLibraries."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        licensing()
        projectComposeResource()
    }
    commonTest()
}