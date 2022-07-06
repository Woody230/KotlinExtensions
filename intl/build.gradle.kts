plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Internationalization."
)

android.setup()

kotlin.setup {
    commonMain()
    commonTest()
}