plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Based on Android intents."
)

android.setup()

kotlin.setup {
    commonMain {
        projectLogging()
    }
    commonTest()
    androidUnitTest()
    jvmTest()
}