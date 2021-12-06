plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android.setup()
kotlin.setup {
    commonMain {
        kodeinDb()
    }
    commonTest()
}