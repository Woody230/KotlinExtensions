plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Fetching image content via Ktor."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        ktorClient()
        projectLogging()
        projectImageModel()
    }
    commonTest()
}