plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "AboutLibraries extensions."
)

android.setup(project)

kotlin.setup {
    commonMain {
        aboutLibraries()
    }
    commonTest()
}