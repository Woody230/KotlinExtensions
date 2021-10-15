plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.5.30"
}

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
}