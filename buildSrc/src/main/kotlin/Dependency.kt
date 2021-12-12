import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

private const val BASE_PUBLISHING_NAME = "ktx"
private const val KTX_DATETIME = "0.3.1"
private const val KTX_SERIALIZATION = "1.3.1"
private const val MOSHI = "1.11.0"
private const val LIFECYCLE = "2.3.1"
private const val CORE = "1.7.0"
private const val ANDROID_TEST = "1.1.0"
private const val ROBOLECTRIC = "4.6.1"
private const val DATASTORE = "1.0.0"
private const val ANDROIDX_PREFERENCE = "1.1.1"
private const val KODEIN_DB = "0.9.0-beta"
private const val CONSTRAINT_LAYOUT = "1.0.0-rc02"
private const val SETTINGS = "0.8.1"
private const val COROUTINE = "1.5.2"
private const val LOGGING = "2.2.0"
const val COMPOSE = "1.0.0"
const val KOTLIN = "1.5.31"

fun KotlinDependencyHandler.ktxDateTime() = api("org.jetbrains.kotlinx:kotlinx-datetime:$KTX_DATETIME")
fun KotlinDependencyHandler.ktxSerialization() = api("org.jetbrains.kotlinx:kotlinx-serialization-json:$KTX_SERIALIZATION")

fun KotlinDependencyHandler.moshi() = api("com.squareup.moshi:moshi-kotlin:$MOSHI")
fun KotlinDependencyHandler.livedata() = api("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE")
fun KotlinDependencyHandler.datastore() = api("androidx.datastore:datastore-preferences:$DATASTORE")
fun KotlinDependencyHandler.androidxPreference() = api("androidx.preference:preference-ktx:$ANDROIDX_PREFERENCE")
fun KotlinDependencyHandler.androidCore() = api("androidx.core:core-ktx:$CORE")
fun KotlinDependencyHandler.kodeinDb() = api("org.kodein.db:kodein-db-api:$KODEIN_DB")
fun KotlinDependencyHandler.coroutine() = api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINE")
fun KotlinDependencyHandler.multiplatformSettings() {
    api("com.russhwolf:multiplatform-settings:$SETTINGS")
    api("com.russhwolf:multiplatform-settings-coroutines:$SETTINGS")
}

fun KotlinDependencyHandler.logging() = implementation("io.github.aakira:napier:$LOGGING")

fun KotlinDependencyHandler.runtime() = api("org.jetbrains.compose.runtime:runtime:$COMPOSE")
fun KotlinDependencyHandler.ui() = api("org.jetbrains.compose.ui:ui:$COMPOSE")
fun KotlinDependencyHandler.material() = api("org.jetbrains.compose.material:material:$COMPOSE")
fun KotlinDependencyHandler.foundation() = api("org.jetbrains.compose.foundation:foundation:$COMPOSE")
fun KotlinDependencyHandler.constraintLayout() = api("androidx.constraintlayout:constraintlayout-compose:$CONSTRAINT_LAYOUT")

fun KotlinDependencyHandler.function() = api(project(":function"))
fun KotlinDependencyHandler.settings() = api(project(":settings"))

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
fun NamedDomainObjectContainer<KotlinSourceSet>.commonTest(block: KotlinDependencyHandler.() -> Unit = {})
{
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
fun NamedDomainObjectContainer<KotlinSourceSet>.jvmMain(block: KotlinDependencyHandler.() -> Unit = {})
{
    getByName("jvmMain") {
        dependencies {
            block(this)
        }
    }
}

/**
 * Sets up JVM test dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.jvmTest(block: KotlinDependencyHandler.() -> Unit = {})
{
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
fun NamedDomainObjectContainer<KotlinSourceSet>.androidMain(block: KotlinDependencyHandler.() -> Unit = {})
{
    getByName("androidMain") {
        dependencies {
            block(this)
        }
    }
}

/**
 * Sets up Android test dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.androidTest(block: KotlinDependencyHandler.() -> Unit = {})
{
    getByName("androidTest") {
        dependencies {
            implementation(kotlin("test-junit"))
            implementation("androidx.test.ext:junit:$ANDROID_TEST")
            implementation("androidx.test:core:$ANDROID_TEST")
            implementation("androidx.test:runner:$ANDROID_TEST")
            implementation("androidx.test:rules:$ANDROID_TEST")
            implementation("org.robolectric:robolectric:$ROBOLECTRIC")
            block(this)
        }
    }
}

/**
 * Sets up Android.
 */
fun LibraryExtension.setup(manifestPath: String = "src/androidMain/AndroidManifest.xml", block: LibraryExtension.() -> Unit = {})
{
    compileSdk = 31
    sourceSets.getByName("main").manifest.srcFile(manifestPath)
    defaultConfig {
        minSdk = 23
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    block(this)
}

/**
 * Sets up Android with Compose.
 */
fun LibraryExtension.setupWithCompose(manifestPath: String = "src/androidMain/AndroidManifest.xml", block: LibraryExtension.() -> Unit = {}) {
    buildFeatures {
        compose = true
    }
    setup(manifestPath, block)
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

/**
 * Sets up the pom.xml that gets created using the maven-publish plugin.
 *
 * @see <a href="https://docs.gradle.org/current/userguide/publishing_customization.html">gradle</a>
 * @see <a href="https://maven.apache.org/pom.html">pom.xml</a>
 */
fun PublishingExtension.publish(project: Project) = publications.withType<MavenPublication>().configureEach {
    pom {
        name.set("${BASE_PUBLISHING_NAME}-${project.name}")
        licenses()
        developers()
    }
}

/**
 * Sets up the pom.xml licenses.
 */
private fun MavenPom.licenses() = licenses {
    license {
        this.name.set("The Apache Software License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("repo")
    }
}

/**
 * Sets up the pom.xml developers.
 */
private fun MavenPom.developers() = developers {
    developer {
        name.set("Brandon Selzer")
    }
}