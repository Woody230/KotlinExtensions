plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "multiplatform-settings extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        ktxSerialization()
        coroutine()
        multiplatformSettings()
        projectValueIdentifier()
    }
    commonTest()
}