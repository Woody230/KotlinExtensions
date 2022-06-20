plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization extensions"
)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
        xmlSerialization()
        projectLogging()
    }
    commonTest()
    jvmTest()
}