plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "AboutLibraries extensions."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        licensing()
    }
    commonTest()
}