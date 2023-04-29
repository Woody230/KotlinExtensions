plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "multiplatform-settings extensions for Compose Multiplatform classes"
)

android.setupWithCompose(project)

kotlin.setup {
    commonMain {
        coroutine()
        projectSettings()
        projectComposeUiGraphics()
    }
    commonTest()
}