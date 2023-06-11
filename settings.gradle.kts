import com.bselzer.gradle.function.settings.substituteModulesUsingProjects

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

includeBuild("convention-plugins")
includeBuild("misc") {
    substituteModulesUsingProjects(
        "io.github.woody230.ktx:aboutlibraries" to ":aboutlibraries",
        "io.github.woody230.ktx:function" to ":function",
        "io.github.woody230.ktx:intl" to ":intl",
        "io.github.woody230.ktx:resource" to ":resource",
        "io.github.woody230.ktx:settings" to ":settings"
    )
}
includeBuild("compose")