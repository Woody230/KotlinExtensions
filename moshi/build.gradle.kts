plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(project)

android.setup()

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    sourceSets {
        androidMain {
            moshi()
        }
        androidTest()
    }
}