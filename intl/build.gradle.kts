plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Internationalization."
)

android.setup(project)

kotlin.setup {
    commonMain()
    commonTest()
}