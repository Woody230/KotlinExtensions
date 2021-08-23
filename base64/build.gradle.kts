plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android.setup()

kotlin.setup {
    commonMain {
        function()
    }
    commonTest()
    jvmTest()
}