allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }

    dependencies {
        classpath(libs.woody230.gradle.internal.multiplatform.compose.plugin)
        classpath(libs.woody230.gradle.internal.multiplatform.compose.test.plugin)
        classpath(libs.ktx.serialization.plugin)
    }
}

tasks.register("publishBuildToMavenCentralRepository") {
    val tasks = getTasksByName("publishAllPublicationsToMavenCentralRepository", true)
    dependsOn(tasks)
}

tasks.register("publishBuildToMavenLocal") {
    val tasks = getTasksByName("publishToMavenLocal", true)
    dependsOn(tasks)
}