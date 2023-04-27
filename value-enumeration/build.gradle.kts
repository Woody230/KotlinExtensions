plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "Value class wrappers for enumerations."
)

android.setup()

kotlin.setup {
    commonMain {
        projectValueIdentifier()
        projectSerialization()
    }
    commonTest()
    androidUnitTest()
    jvmTest()
}