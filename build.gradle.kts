plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

allprojects {
    group = "com.bselzer.${Metadata.BASE_PUBLISHING_NAME}"
    version = Metadata.VERSION

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

android.setup(manifestPath = "buildSrc/src/androidMain/AndroidManifest.xml")
kotlin.setup()