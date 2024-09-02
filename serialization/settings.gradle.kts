pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("io.github.woody230.gradle.internal.bundled") version "1.5.0"
}

include("core")
include("json")
include("xml")
include("yaml")
include("yaml-json-conversion")