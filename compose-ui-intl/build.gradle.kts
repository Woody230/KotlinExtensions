plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Jetbrains Compose internationalization."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        ui()
        projectIntl()
    }
    commonTest()
}