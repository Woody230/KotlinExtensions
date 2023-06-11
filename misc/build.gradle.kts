buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    dependencies {
        classpath(libs.woody230.gradle.internal.android.desugar.plugin)
        classpath(libs.woody230.gradle.internal.moko.resources.plugin)
    }
}