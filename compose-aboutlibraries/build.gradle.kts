plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "AboutLibraries composables using Jetbrains Compose."
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