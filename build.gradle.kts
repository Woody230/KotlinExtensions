plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

allprojects {
    group = "com.bselzer.library.kotlin.extension"
    version = "2.1.0"

    apply(plugin = "maven-publish")

    repositories {
        google()
        mavenCentral()
    }
}

android.setup(manifestPath = "buildSrc/src/androidMain/AndroidManifest.xml")
kotlin.setup()