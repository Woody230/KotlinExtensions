plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation(libs.woody230.gradle.android.desugar.plugin)
    implementation(libs.woody230.gradle.android.plugin)
    implementation(libs.woody230.gradle.moko.resources.plugin)
    implementation(libs.woody230.gradle.multiplatform.compose.plugin)
    implementation(libs.woody230.gradle.multiplatform.compose.test.plugin)
    implementation(libs.woody230.gradle.multiplatform.plugin)
    implementation(libs.woody230.gradle.multiplatform.publish.plugin)
    implementation(libs.woody230.gradle.multiplatform.test.plugin)

    // TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}