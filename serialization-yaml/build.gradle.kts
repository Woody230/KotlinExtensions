plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization extensions for Him188.yamlkt"
)

android.setup()

kotlin.setup {
    commonMain {
        projectSerialization()
    }
    commonTest()
    androidTest {
        implementation("com.charleskorn.kaml:kaml:0.34.0")
        implementation("org.yaml:snakeyaml:1.26")
    }
    jvmTest {
        implementation("com.charleskorn.kaml:kaml:0.34.0")
        implementation("org.yaml:snakeyaml:1.26")
    }
}