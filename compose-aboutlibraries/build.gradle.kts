plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Composable implementation of AboutLibraries using Jetbrains Compose."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectAboutLibraries()
        projectComposeResource()
        projectComposeUiLayoutCustom()
    }
    commonTest()
}