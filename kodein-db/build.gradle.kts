plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(project)

android.setup()

kotlin.setup {
    commonMain {
        kodeinDb()
        projectValue()
    }
    commonTest()
}