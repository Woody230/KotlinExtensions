plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Internationalization through locale support."
)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
}