plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

android.setup()

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        androidMain {
            livedata()
        }
        androidTest()
    }
}