plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Client side Ktor extensions."
)

android.setup()

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

kotlin.setup {
    commonMain {
        ktorClient()
    }
    commonTest {
        coroutine()
        ktorMockEngine()
    }
    androidTest {
        ktorOkHttpEngine()
    }
    jvmTest {
        ktorOkHttpEngine()
    }
}