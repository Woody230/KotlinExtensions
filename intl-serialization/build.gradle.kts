plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "kotlinx.serialization for internationalization"
)

android.setup()

kotlin.setup {
    commonMain {
        ktxSerialization()
        projectIntl()
    }
    commonTest()
}