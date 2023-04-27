import Versions.ANDROID_COMPOSE
import Versions.ANDROID_TEST_JUNIT
import Versions.ANDROID_TEST_CORE
import Versions.ANDROID_TEST_RUNNER
import Versions.ANDROID_TEST_RULES
import Versions.COMPOSE_COMPILER
import Versions.ROBOLECTRIC
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * Sets up common dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.commonMain(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("commonMain") {
        dependencies {
            implementation(kotlin("stdlib-common"))
            block(this)
        }
    }
}

/**
 * Sets up common test dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.commonTest(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("commonTest") {
        dependencies {
            implementation(kotlin("test-common"))
            implementation(kotlin("test-annotations-common"))
            block(this)
        }
    }
}

/**
 * Sets up JVM dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.jvmMain(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("jvmMain") {
        dependencies {
            block(this)
        }
    }
}

/**
 * Sets up JVM test dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.jvmTest(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("jvmTest") {
        dependencies {
            implementation(kotlin("test-junit"))
            block(this)
        }
    }
}

/**
 * Sets up Android dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.androidMain(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("androidMain") {
        dependencies {
            block(this)
        }
    }
}

/**
 * Sets up Android test dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.androidUnitTest(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("androidUnitTest") {
        dependencies {
            implementation(kotlin("test-junit"))
            implementation("androidx.test.ext:junit:$ANDROID_TEST_JUNIT")
            implementation("androidx.test:core:$ANDROID_TEST_CORE")
            implementation("androidx.test:runner:$ANDROID_TEST_RUNNER")
            implementation("androidx.test:rules:$ANDROID_TEST_RULES")
            implementation("org.robolectric:robolectric:$ROBOLECTRIC")
            implementation("androidx.compose.ui:ui-test:$ANDROID_COMPOSE")
            implementation("androidx.compose.ui:ui-test-junit4:$ANDROID_COMPOSE")
            block(this)
        }
    }
}

/**
 * Sets up Android.
 */
fun LibraryExtension.setup(block: LibraryExtension.() -> Unit = {}) {
    compileSdk = 33
    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    publishing {
        multipleVariants {
            allVariants()
            withSourcesJar()
            withJavadocJar()
        }
    }
    block(this)
}

/**
 * Sets up Android with Compose.
 */
fun LibraryExtension.setupWithCompose(block: LibraryExtension.() -> Unit = {}) {
    buildFeatures {
        compose = true
    }
    composeOptions {
        // https://mvnrepository.com/artifact/org.jetbrains.compose.compiler/compiler
        // https://github.com/JetBrains/compose-multiplatform/blob/master/gradle-plugins/compose/src/main/kotlin/org/jetbrains/compose/ComposeCompilerCompatibility.kt
        kotlinCompilerExtensionVersion = COMPOSE_COMPILER
    }
    setup(block)
}

/**
 * Sets up Kotlin multiplatform targets.
 */
private fun KotlinMultiplatformExtension.targets() {
    jvm()
    android {
        publishLibraryVariants("release", "debug")
    }
}

/**
 * Sets up Kotlin multiplatform.
 */
fun KotlinMultiplatformExtension.setup(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>.() -> Unit = {}) {
    targets()
    sourceSets(this.sourceSets)
}