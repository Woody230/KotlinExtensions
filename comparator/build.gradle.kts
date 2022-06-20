plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Object comparators."
)

android.setup()

kotlin.setup {
    commonMain {
        projectFunction()
    }
    commonTest()
}