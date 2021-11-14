plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

//android.setupWithCompose()
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