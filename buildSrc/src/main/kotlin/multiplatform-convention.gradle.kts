import Metadata.COMPILE_SDK
import Metadata.JAVA_VERSION
import Metadata.JVM_TARGET
import Metadata.MIN_SDK
import Metadata.NAMESPACE_ID
import Metadata.SUBGROUP_ID
import org.gradle.accessors.dm.LibrariesForLibs
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {
    // TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
    id("org.jetbrains.kotlin.multiplatform")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = Metadata.JVM_TARGET
}

with (kotlin) {
    targets()
    jvmToolchain(JVM_TARGET.toInt())
    sourceSets.addDependencies()
}

fun KotlinMultiplatformExtension.targets() {
    jvm()
    android {
        publishLibraryVariants("release", "debug")
    }
}

fun NamedDomainObjectContainer<KotlinSourceSet>.addDependencies() {
    getByName("commonMain").dependencies {
        api(libs.bundles.common)
    }
    getByName("commonTest").dependencies {
        implementation(libs.bundles.common.test)
    }
    getByName("androidUnitTest").dependencies {
        implementation(libs.bundles.android.unit.test)
    }
    getByName("jvmTest").dependencies {
        implementation(libs.bundles.jvm.test)
    }
}