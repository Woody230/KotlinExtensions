tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = Metadata.JVM_TARGET
}

allprojects {
    group = Metadata.GROUP_ID
    version = Metadata.VERSION

    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    val jar by project.tasks.registering(org.gradle.api.tasks.bundling.Jar::class) {
        archiveClassifier.set("javadoc")
    }

    ext.set("jar", jar)
}