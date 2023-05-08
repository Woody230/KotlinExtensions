allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// TODO must use root project: extension libs does not exist https://github.com/gradle/gradle/issues/18237
subprojects {
    apply(plugin = rootProject.libs.plugins.woody230.android.asProvider().get().pluginId)
    apply(plugin = rootProject.libs.plugins.woody230.multiplatform.get().pluginId)
    apply(plugin = rootProject.libs.plugins.woody230.publish.get().pluginId)
}