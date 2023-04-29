plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "kotlinx.datetime extensions"
)

android.setup(project) {
    compileOptions {
        // DateTimeFormatter requires API level 26+ otherwise
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.3")
    }
}

kotlin.setup {
    commonMain {
        ktxDateTime()
        coroutine()
    }
    commonTest {
        projectIntl()
    }
    androidUnitTest()
    jvmTest()
}