plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(project)

android.setup()

dependencies {
    testImplementation(project(mapOf("path" to ":ktx-intent")))
}

kotlin.setup {
    commonMain {
        ktorClient()
        projectIntent()
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