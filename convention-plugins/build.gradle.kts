plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.woody230.gradle.internal.android.desugar.plugin)
    implementation(libs.woody230.gradle.internal.android.plugin)
    implementation(libs.woody230.gradle.internal.moko.resources.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.compose.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.compose.test.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.publish.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.test.plugin)

    // TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}