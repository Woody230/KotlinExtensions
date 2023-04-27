plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Base extensions for laying out Jetbrains Compose UI."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        material()
        foundation()
        projectComposeUi()
    }
    commonTest()
    androidUnitTest()
    jvmTest()
}