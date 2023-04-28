plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Extensions for base64 encoding and decoding between strings and byte arrays."
)

android.setup()

kotlin.setup {
    commonMain()
    commonTest {
        projectFunction()
    }
    androidUnitTest()
    jvmTest()
}