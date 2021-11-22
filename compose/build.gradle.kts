plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android.setupWithCompose()

kotlin.setup {
    commonMain()
    androidMain {
        preference()
        androidCompose()
    }
    commonTest()
}