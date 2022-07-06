pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
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
include("logging")
include("molecule")
include("resource")
include("serialization")
include("settings")
include("value")
