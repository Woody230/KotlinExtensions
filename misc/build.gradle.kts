buildscript {
    dependencies {
        classpath(libs.moko.resources.generator)
        classpath(libs.kotlin.dsl.plugin)
        classpath(libs.woody230.gradle.internal.android.desugar.plugin)
    }
}