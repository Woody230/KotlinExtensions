plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(project)

android.setup()

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