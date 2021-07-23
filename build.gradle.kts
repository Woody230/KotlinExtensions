plugins {
    kotlin("multiplatform") version "1.5.10"
    id("com.android.library")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

allprojects {
    group = "com.bselzer.library.kotlin.extension"
    version = "1.1.0"

    apply(plugin = "maven-publish")

    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}