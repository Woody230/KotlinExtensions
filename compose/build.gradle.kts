plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version COMPOSE
}

publishing.publish(project)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        runtime()
        ui()
        material()
        foundation()

        projectFunction()
    }
    androidMain {
        constraintLayout()
    }
    jvmMain()
    commonTest()
    androidTest()
    jvmTest()
}