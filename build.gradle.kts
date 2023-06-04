allprojects {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
}

tasks.register("publishBuildsToMavenCentralRepository") {
    val tasks = gradle.includedBuilds.map { build -> build.task(":publishBuildToMavenCentralRepository") }
    dependsOn(tasks)
}

tasks.register("publishBuildsToMavenLocal") {
    val tasks = gradle.includedBuilds.map { build -> build.task(":publishBuildToMavenLocal") }
    dependsOn(tasks)
}