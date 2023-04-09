plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Code generation models."
)

android.setup()

kotlin.setup {
    commonMain()
    commonTest()
}