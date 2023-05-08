package io.github.woody230.gradle.kotlin.multiplatform

import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler


class KotlinMultiplatformDependencies(
    private val extension: KotlinProjectExtension
) {
    fun commonMain(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.commonMain.dependencies(configure)
    fun commonTest(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.commonTest.dependencies(configure)
    fun jvmMain(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.jvmMain.dependencies(configure)
    fun jvmTest(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.jvmTest.dependencies(configure)
    fun androidMain(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.androidMain.dependencies(configure)
    fun androidUnitTest(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.androidUnitTest.dependencies(configure)
    fun androidInstrumentedTest(configure: KotlinDependencyHandler.() -> Unit) = extension.sourceSets.androidInstrumentedTest.dependencies(configure)
}

fun Project.kotlinMultiplatformDependencies(configure: KotlinMultiplatformDependencies.() -> Unit) = KotlinMultiplatformDependencies(kotlinExtension).apply(configure)