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
include("compose-aboutlibraries")
include("compose-accompanist")
include("compose-constraint-layout")
include("compose-resource")
include("compose-serialization")
include("compose-settings")
include("compose-ui")
include("compose-ui-geometry")
include("compose-ui-graphics")
include("compose-ui-intl")
include("compose-ui-layout")
include("compose-ui-layout-common")
include("compose-ui-layout-custom")
include("compose-ui-text")
include("compose-ui-unit")