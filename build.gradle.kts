allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// TODO must use root project: extension libs does not exist https://github.com/gradle/gradle/issues/18237
subprojects {
    apply(plugin = rootProject.libs.plugins.woody230.android.get().pluginId)
    apply(plugin = rootProject.libs.plugins.woody230.multiplatform.get().pluginId)
    apply(plugin = rootProject.libs.plugins.vanniktech.publish.get().pluginId)
    apply(plugin = rootProject.libs.plugins.dokka.get().pluginId)

    // TODO temporarily explicitly declare dependency
    tasks.whenTaskAdded {
        if (name == "generateMRandroidMain") {
            tasks.withType<org.gradle.jvm.tasks.Jar>().forEach { task -> task.dependsOn(this) }
        }
    }
}