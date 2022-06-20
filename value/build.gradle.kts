plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Value class extensions."
)

android.setup()

kotlin.setup {
    commonMain()
    commonTest()
}