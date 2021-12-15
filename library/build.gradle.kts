plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version COMPOSE
}

publishing.publish(project)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        licensing()
        projectCompose()
    }
    commonTest()
}