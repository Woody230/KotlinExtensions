pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "KotlinExtensions"
include("base64")
include("comparator")
include("compose")
include("compose-constraint-layout")
include("compose-accompanist")
include("compose-image")
include("compose-resource")
include("coroutine")
include("datetime")
include("function")
include("geometry")
include("kodein-db")
include("ktor-client")
include("library")
include("livedata")
include("logging")
include("moshi")
include("preference")
include("resource")
include("serialization")
include("settings")
include("settings-compose")
include("value")
