plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version KOTLIN
}

android.setup()
kotlin.setup {
    commonMain {
        ktxSerialization()
        settings()
    }
    commonTest()
    jvmTest()
}