package io.github.woody230.ktx.convention

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("io.github.woody230.ktx.convention.multiplatform")
    id("org.jetbrains.kotlin.plugin.serialization")
}

multiplatformPublishExtension {
    coordinates.module.set("serialization-$name")
}