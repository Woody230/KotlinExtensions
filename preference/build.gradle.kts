plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "AndroidX preference and DataStore extensions."
)

android.setup()

kotlin.setup {
    commonMain()
    commonTest()
    androidMain {
        datastore()
        androidxPreference()
    }
    androidTest {
    }
}