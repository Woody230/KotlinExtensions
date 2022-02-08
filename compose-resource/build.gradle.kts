plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jetbrains.compose") version COMPOSE
}

publishing.publish(project)

android.setupWithCompose()

kotlin.setup {
    commonMain {
        projectCompose()

        resourcesCompose()
        projectResource()
    }
    commonTest()
    androidTestWithCompose()
    jvmTest()
}