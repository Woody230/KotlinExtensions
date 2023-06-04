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