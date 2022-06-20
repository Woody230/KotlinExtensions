plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "Two and three dimensional geometrical objects."
)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
    }
    commonTest()
    jvmTest()
}