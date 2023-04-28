plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Value class wrappers for identifiers."
)

android.setup(project)

kotlin.setup {
    commonMain()
    commonTest()
}