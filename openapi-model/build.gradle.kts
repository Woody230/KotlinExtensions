plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "OpenApi models."
)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
}