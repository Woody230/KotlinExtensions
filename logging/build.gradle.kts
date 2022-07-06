plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Logging wrapper around Napier."
)

android.setup()

kotlin.setup {
    commonMain {
        napier()
    }
    commonTest()
}