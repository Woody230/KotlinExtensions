package io.github.woody230.ktx.convention

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("io.github.woody230.ktx.convention.multiplatform")
}

multiplatformPublishExtension {
    coordinates.module.set("store-$name")
}