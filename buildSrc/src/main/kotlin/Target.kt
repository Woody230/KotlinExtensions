import Metadata.COMPILE_SDK
import Metadata.JAVA_VERSION
import Metadata.JVM_TARGET
import Metadata.MIN_SDK
import Metadata.SUBGROUP_ID
import Metadata.NAMESPACE_ID
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import java.io.File

/**
 * Sets up common dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.commonMain(block: KotlinDependencyHandler.() -> Unit = {}) {
    getByName("commonMain") {
        dependencies {
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
            block(this)
        }
    }
}

/**
 * Sets up Android.
 */
fun LibraryExtension.setup(project: Project, block: LibraryExtension.() -> Unit = {}) {
    block(this)
}

/**
 * Sets up Android with Compose.
 */
fun LibraryExtension.setupCompose(compilerVersion: Provider<String>) {
    buildFeatures {
        compose = true
    }
    composeOptions {
        // https://mvnrepository.com/artifact/org.jetbrains.compose.compiler/compiler
        // https://github.com/JetBrains/compose-multiplatform/blob/master/gradle-plugins/compose/src/main/kotlin/org/jetbrains/compose/ComposeCompilerCompatibility.kt
        kotlinCompilerExtensionVersion = compilerVersion.get()
    }
}

fun LibraryExtension.setupDesugaring(project: Project, dependency: Provider<MinimalExternalModuleDependency>) {
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }
    project.dependencies {
        add("coreLibraryDesugaring", dependency)
    }
}

fun LibraryExtension.setupMokoResources(project: Project) = with(sourceSets.getByName("main")) {
    // TODO temporary srcDir inclusion https://github.com/icerockdev/moko-resources/issues/353
    val buildDir = project.buildDir
    assets.srcDir(File(buildDir, "generated/moko/androidMain/assets"))
    res.srcDir(File(buildDir, "generated/moko/androidMain/res"))
}

/**
 * Sets up Kotlin multiplatform.
 */
fun KotlinMultiplatformExtension.setup(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>.() -> Unit = {}) {
    sourceSets(this.sourceSets)
}