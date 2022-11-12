plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Versions.KOTLIN
}

// This module has a copy of v0.12.0 https://github.com/Him188/yamlkt with minor modifications to make it usable for multiplatform.
publishing.publish(
    project = project,
    description = "kotlinx.serialization extensions for Him188.yamlkt"
) {
    developer {
        name.set("Him188")
    }
}

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