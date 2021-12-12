plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version KOTLIN
}

publishing.publish(project)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
        coroutine()
        multiplatformSettings()
    }
    commonTest()
}