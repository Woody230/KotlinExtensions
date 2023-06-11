pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("io.github.woody230.gradle.internal.bundled") version "1.1.0"
}

rootProject.name = "compose"
include("aboutlibraries")
include("accompanist")
include("constraint-layout")
include("resource")
include("serialization")
include("settings")
include("ui")
include("ui-geometry")
include("ui-graphics")
include("ui-intl")
include("ui-layout")
include("ui-layout-common")
include("ui-layout-custom")
include("ui-text")
include("ui-unit")