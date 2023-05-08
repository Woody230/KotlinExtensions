package io.github.woody230.gradle.kotlin.multiplatform

import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

val NamedDomainObjectContainer<KotlinSourceSet>.commonMain: KotlinSourceSet
    get() = getByName("commonMain")

fun NamedDomainObjectContainer<KotlinSourceSet>.commonMain(configure: KotlinSourceSet.() -> Unit) = commonMain.apply(configure)


val NamedDomainObjectContainer<KotlinSourceSet>.commonTest: KotlinSourceSet
    get() = getByName("commonTest")

fun NamedDomainObjectContainer<KotlinSourceSet>.commonTest(configure: KotlinSourceSet.() -> Unit) = commonTest.apply(configure)



val NamedDomainObjectContainer<KotlinSourceSet>.jvmMain: KotlinSourceSet
    get() = getByName("jvmMain")

fun NamedDomainObjectContainer<KotlinSourceSet>.jvmMain(configure: KotlinSourceSet.() -> Unit) = jvmMain.apply(configure)



val NamedDomainObjectContainer<KotlinSourceSet>.jvmTest: KotlinSourceSet
    get() = getByName("jvmTest")

fun NamedDomainObjectContainer<KotlinSourceSet>.jvmTest(configure: KotlinSourceSet.()-> Unit) = jvmTest.apply(configure)



val NamedDomainObjectContainer<KotlinSourceSet>.androidMain: KotlinSourceSet
    get() = getByName("androidMain")

fun NamedDomainObjectContainer<KotlinSourceSet>.androidMain(configure: KotlinSourceSet.() -> Unit) = androidMain.apply(configure)



val NamedDomainObjectContainer<KotlinSourceSet>.androidUnitTest: KotlinSourceSet
    get() = getByName("androidUnitTest")

fun NamedDomainObjectContainer<KotlinSourceSet>.androidUnitTest(configure: KotlinSourceSet.() -> Unit) = androidUnitTest.apply(configure)



val NamedDomainObjectContainer<KotlinSourceSet>.androidInstrumentedTest: KotlinSourceSet
    get() = getByName("androidInstrumentedTest")

fun NamedDomainObjectContainer<KotlinSourceSet>.androidInstrumentedTest(configure: KotlinSourceSet.() -> Unit) = androidInstrumentedTest.apply(configure)