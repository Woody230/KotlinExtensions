plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Wrappers for strings and images using the compose and resource modules."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectResource()
        projectComposeUiLayoutCommon()
        projectComposeUiIntl()
    }
    commonTest()
    androidUnitTest()
    jvmTest()
}