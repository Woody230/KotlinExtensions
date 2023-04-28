plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Object comparators."
)

android.setup(project)

kotlin.setup {
    commonMain {
        projectFunction()
    }
    commonTest()
}