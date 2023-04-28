plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Common composable implementations for Compose Multiplatform."
)

android.setupWithCompose(project)

kotlin.setup {
    commonMain {
        projectConstraintLayout()
        projectFunction()
        projectComposeUiLayout()
    }
    commonTest()
}