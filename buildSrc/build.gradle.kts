plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven("https://plugins.gradle.org/m2/")
}

buildscript {
    dependencies {
        classpath(libs.moko.resources.generator)
    }
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.android.gradle.plugin)
    implementation(libs.vanniktech.publish.gradle.plugin)
    implementation(libs.dokka.gradle.plugin)
    implementation(libs.moko.resources.gradle.plugin)

    // TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}