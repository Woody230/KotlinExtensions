plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization for kotlinx.datetime"
)

android.setup(project)

kotlin.setup {
    commonMain {
        ktxSerialization()
        projectDateTime()
    }
    commonTest()
    androidUnitTest()
    jvmTest()
}