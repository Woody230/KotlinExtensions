package io.github.woody230.ktx

import gradle.kotlin.dsl.accessors._1c8707824c48c16ed0a1f292cf6be26b.kotlin
import gradle.kotlin.dsl.accessors._80a2ae57395e1362b61311ead0eb480f.android
import org.gradle.kotlin.dsl.withType

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("dev.icerock.mobile.multiplatform-resources")
}

// TODO temporary srcDir inclusion https://github.com/icerockdev/moko-resources/issues/353
with(android.sourceSets.getByName("main")) {
    assets.srcDir(File(buildDir, "generated/moko/androidMain/assets"))
    res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
}

// TODO temporarily explicitly declare dependency
tasks.whenTaskAdded {
    if (name == "generateMRandroidMain") {
        tasks.withType<org.gradle.jvm.tasks.Jar>().forEach { task -> task.dependsOn(this) }
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().forEach { task ->
    task.dependsOn("generateMRandroidMain")
    task.dependsOn("generateMRjvmMain")
}