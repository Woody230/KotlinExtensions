plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version KOTLIN
}

publishing.publish(project)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
    jvmTest()
}