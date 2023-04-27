plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "General Kotlin standard library extensions."
)

android.setup()

kotlin.setup {
    commonMain()
    androidMain {
        androidCore()
    }
    jvmMain()
    commonTest()
    androidUnitTest()
    jvmTest()
}