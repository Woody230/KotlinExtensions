plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version COMPOSE
}

android.setupWithCompose()

kotlin.setup {
    commonMain {
        runtime()
        ui()
        material()
        foundation()

        function()
    }
    androidMain {
        constraintLayout()
    }
    jvmMain()
    commonTest()
}