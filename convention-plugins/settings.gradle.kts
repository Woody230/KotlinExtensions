pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    // TODO https://github.com/gradle/gradle/issues/15383
    // https://github.com/radoslaw-panuszewski/typesafe-conventions-gradle-plugin
    id("dev.panuszewski.typesafe-conventions") version "0.11.1"

    // NOTE: must be after typesafe conventions because it creates libs version catalog
    id("io.github.woody230.gradle.internal.bundled") version "2.0.0"
}