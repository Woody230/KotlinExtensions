plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "coroutine extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        coroutine()
    }
    commonTest()
}