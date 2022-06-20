plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Base64 encoding and decoding between strings and byte arrays using v0.0.1 of kbase64."
) {
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