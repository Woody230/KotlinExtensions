pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "KotlinExtensions"
include("comparator")
include("function")
include("moshi")
include("livedata")
include("serialization")
include("datetime")
include("base64")
include("preference")
include("geometry")
include("kodein-db")
include("compose")
include("settings")
include("logging")