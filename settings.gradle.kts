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

fun ConfigurableIncludedBuild.substitute(substitution: Pair<String, String>) {
    dependencySubstitution {
        val module = module(substitution.first)
        val project = project(substitution.second)
        substitute(module).using(project)
    }
}

includeBuild("convention-plugins")
includeBuild("misc") {
    substitute("io.github.woody230.ktx:aboutlibraries" to ":aboutlibraries")
    substitute("io.github.woody230.ktx:function" to ":function")
    substitute("io.github.woody230.ktx:intl" to ":intl")
    substitute("io.github.woody230.ktx:resource" to ":resource")
    substitute("io.github.woody230.ktx:settings" to ":settings")
}
includeBuild("compose")
