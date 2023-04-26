plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Value class wrappers for identifiers."
)

android.setup()

kotlin.setup {
    commonMain()
    commonTest()
}