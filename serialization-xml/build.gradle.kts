plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization extensions for pdvrieze.xmlutil"
)

android.setup()

kotlin.setup {
    commonMain {
        xmlSerialization()
        projectLogging()
    }
    commonTest()
    jvmTest()
}