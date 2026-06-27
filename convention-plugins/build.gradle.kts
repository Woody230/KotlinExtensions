plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.ktx.serialization.plugin)
    implementation(libs.moko.resources.generator)
    implementation(libs.woody230.gradle.internal.android.desugar.plugin)
    implementation(libs.woody230.gradle.internal.android.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.compose.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.compose.test.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.publish.plugin)
    implementation(libs.woody230.gradle.internal.multiplatform.test.plugin)
}