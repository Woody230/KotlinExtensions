plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "kotlinx.datetime extensions"
)

android.setup {
    compileOptions {
        // DateTimeFormatter requires API level 26+ otherwise
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    }
}

kotlin.setup {
    commonMain {
        ktxDateTime()
        coroutine()
        projectIntl()
    }
    commonTest()
    androidTest()
    jvmTest()
}