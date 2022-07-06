plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "Image models."
)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
}