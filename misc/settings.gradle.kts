pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("io.github.woody230.gradle.internal.bundled") version "1.3.0"
}

include("aboutlibraries")
include("base64")
include("comparator")
include("coroutine")
include("datetime")
include("datetime-serialization")
include("function")
include("geometry")
include("geometry-serialization")
include("image-kodein-db")
include("image-ktor-client")
include("image-model")
include("intent")
include("intl")
include("intl-serialization")
include("kodein-db")
include("ktor-client")
include("ktor-client-connectivity")
include("logging")
include("resource")
include("settings")
include("value-enumeration")
include("value-identifier")