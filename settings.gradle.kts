pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.4.0")
}

// TODO feature preview https://docs.gradle.org/8.1.1/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "KotlinExtensions"
include("aboutlibraries")
include("base64")
include("comparator")
include("compose-aboutlibraries")
include("compose-accompanist")
include("compose-constraint-layout")
include("compose-resource")
include("compose-serialization")
include("compose-settings")
include("compose-ui")
include("compose-ui-intl")
include("compose-ui-geometry")
include("compose-ui-graphics")
include("compose-ui-layout")
include("compose-ui-layout-common")
include("compose-ui-layout-custom")
include("compose-ui-text")
include("compose-ui-unit")
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
include("serialization")
include("serialization-xml")
include("settings")
include("value-enumeration")
include("value-identifier")
