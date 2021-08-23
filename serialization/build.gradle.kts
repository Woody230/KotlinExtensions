plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.5.20"
}

android.setup()
kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
    jvmTest()
}