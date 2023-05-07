import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

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

// TODO extension libs does not exist => use variable before subprojects https://github.com/gradle/gradle/issues/18237
val librariesForLibs = libs
subprojects {
    apply(plugin = librariesForLibs.plugins.android.library.get().pluginId)
    apply(plugin = librariesForLibs.plugins.multiplatform.get().pluginId)
    apply(plugin = librariesForLibs.plugins.vanniktech.publish.get().pluginId)
    apply(plugin = librariesForLibs.plugins.dokka.get().pluginId)

    val multiplatform = kotlinExtension as org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
    multiplatform.jvm()
    multiplatform.android { publishLibraryVariants("release", "debug") }

    kotlinExtension.sourceSets.getByName("commonMain").dependencies {
        api(librariesForLibs.bundles.common)
    }

    kotlinExtension.sourceSets.getByName("commonTest").dependencies {
        implementation(librariesForLibs.bundles.common.test)
    }

    kotlinExtension.sourceSets.getByName("androidUnitTest").dependencies {
        implementation(librariesForLibs.bundles.android.unit.test)
    }

    kotlinExtension.sourceSets.getByName("jvmTest").dependencies {
        implementation(librariesForLibs.bundles.jvm.test)
    }

    // TODO temporarily explicitly declare dependency
    tasks.whenTaskAdded {
        if (name == "generateMRandroidMain") {
            tasks.withType<org.gradle.jvm.tasks.Jar>().forEach { task -> task.dependsOn(this) }
        }
    }
}