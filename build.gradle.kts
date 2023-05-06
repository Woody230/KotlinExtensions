tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = Metadata.JVM_TARGET
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

subprojects {
    // TODO temporarily explicitly declare dependency
    tasks.whenTaskAdded {
        if (name == "generateMRandroidMain") {
            tasks.withType<org.gradle.jvm.tasks.Jar>().forEach { task -> task.dependsOn(this) }
        }
    }
}