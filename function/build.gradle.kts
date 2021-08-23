plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

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