plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "Kodein-DB extensions."
)

android.setup()

kotlin.setup {
    commonMain {
        kodeinDb()
        projectValueIdentifier()
    }
    commonTest()
}