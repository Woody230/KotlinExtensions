plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        ktxSerialization()
        projectLogging()
    }
    commonTest {
        xmlSerialization()
    }
    androidUnitTest()
    jvmTest()
}