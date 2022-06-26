plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
}

publishing.publish(
    project = project,
    description = "Extensions, wrappers, and composable implementations for Jetbrains Compose."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        runtime()
        ui()
        material()
        foundation()

        projectConstraintLayout()
        projectFunction()
        projectIntl()
    }
    jvmMain()
    commonTest()
    androidTestWithCompose()
    jvmTest()
}