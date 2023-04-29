plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Base extensions for laying out Compose Multiplatform UI."
)

android.setupWithCompose(project)

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