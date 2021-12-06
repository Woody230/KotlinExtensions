plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android.setup()

kotlin.setup {
    commonMain {
        coroutine()
    }
    commonTest()
}