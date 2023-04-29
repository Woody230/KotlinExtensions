plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Storing image content via Kodein-DB."
)

android.setup(project)

kotlin.setup {
    commonMain {
        projectKodeinDb()
        projectImageModel()
    }
    commonTest()
}