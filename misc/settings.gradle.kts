pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

// TODO feature preview https://docs.gradle.org/8.1.1/userguide/declaring_dependencies.html#sec:type-safe-project-accessors
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "misc"

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
include("serialization-core")
include("serialization-json")
include("serialization-xml")
include("serialization-yaml")
include("serialization-yaml-json-conversion")
include("settings")
include("value-enumeration")
include("value-identifier")