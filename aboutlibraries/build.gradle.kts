plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "AboutLibraries extensions."
)

android.setup()

kotlin.setup {
    commonMain {
        aboutLibraries()
    }
    commonTest()
}