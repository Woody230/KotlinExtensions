plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose") version COMPOSE
}

android.setupWithCompose()
kotlin.setup {
    commonMain {
        runtime()
        coroutine()
        settings()
    }
    commonTest()
}