plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version KOTLIN
}

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
}