plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version Versions.COMPOSE
    kotlin("plugin.serialization") version Versions.KOTLIN
}

publishing.publish(project)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        runtime()
        ui()
        material()
        foundation()

        ktxSerialization()
        projectConstraintLayout()
        projectFunction()
        projectIntl()
    }
    jvmMain()
    commonTest()
    androidTestWithCompose()
    jvmTest()
}