package io.github.woody230.ktx

import io.github.woody230.ktx.Metadata.JVM_TARGET
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JVM_TARGET
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