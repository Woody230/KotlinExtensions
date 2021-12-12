plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(project)

android.setup()

kotlin.setup {
    commonMain()
    androidMain {
        androidCore()
    }
    jvmMain()
    commonTest()
    jvmTest()
}