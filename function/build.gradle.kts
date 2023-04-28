plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "General Kotlin standard library extensions."
)

android.setup(project)

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