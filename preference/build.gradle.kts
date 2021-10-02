plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android.setup()
kotlin.setup {
    commonMain()
    commonTest()
    androidMain {
        datastore()
        androidxPreference()
        compose()
    }
    androidTest {
        composeTest()
    }
}