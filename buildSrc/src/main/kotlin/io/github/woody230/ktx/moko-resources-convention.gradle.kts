package io.github.woody230.ktx

import libs
import org.gradle.kotlin.dsl.withType

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("dev.icerock.mobile.multiplatform-resources")
}

// TODO temporarily explicitly declare dependency
tasks.whenTaskAdded {
    if (name == "generateMRandroidMain") {
        tasks.withType<org.gradle.jvm.tasks.Jar>().forEach { task -> task.dependsOn(this) }
    }
}