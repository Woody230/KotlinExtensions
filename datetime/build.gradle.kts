plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.5.30"
}

android.setup()

kotlin.setup {
    commonMain {
        ktxDateTime()
        ktxSerialization()
    }
    commonTest()
}