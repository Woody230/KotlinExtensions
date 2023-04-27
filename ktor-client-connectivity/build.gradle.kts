plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Determine what connection capabilities exist using Ktor."
)

android.setup()

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
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
    androidUnitTest {
        ktorOkHttpEngine()
    }
    jvmTest {
        ktorOkHttpEngine()
    }
}