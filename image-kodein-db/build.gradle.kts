plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Storing image content via Kodein-DB."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectKodeinDb()
        projectImageModel()
    }
    commonTest()
}