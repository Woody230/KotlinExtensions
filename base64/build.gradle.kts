plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(project) {
    developer {
        name.set("jershell")
    }
}

android.setup()

kotlin.setup {
    commonMain {
        projectFunction()
    }
    commonTest()
    jvmTest()
}