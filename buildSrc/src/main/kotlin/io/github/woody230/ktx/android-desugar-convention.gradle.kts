package io.github.woody230.ktx

import org.gradle.kotlin.dsl.dependencies
import gradle.kotlin.dsl.accessors._80a2ae57395e1362b61311ead0eb480f.android

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.android.library")
}

with(android) {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    project.dependencies {
        add("coreLibraryDesugaring", libs.android.desugar.get())
    }
}