import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

private const val KTX_DATETIME = "0.2.1"
private const val KTX_SERIALIZATION = "1.2.2"
private const val MOSHI = "1.11.0"
private const val LIFECYCLE = "2.3.1"
private const val COMPOSE = "1.0.1"
private const val CORE = "1.6.0"
private const val ANDROID_TEST = "1.1.0"
private const val ROBOLECTRIC = "4.6.1"
private const val DATASTORE = "1.0.0"
private const val ANDROIDX_PREFERENCE = "1.1.1"

fun KotlinDependencyHandler.function() = implementation(project(":function"))
fun KotlinDependencyHandler.ktxDateTime() = implementation("org.jetbrains.kotlinx:kotlinx-datetime:$KTX_DATETIME")
fun KotlinDependencyHandler.ktxSerialization() = implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$KTX_SERIALIZATION")
fun KotlinDependencyHandler.moshi() = implementation("com.squareup.moshi:moshi-kotlin:$MOSHI")
fun KotlinDependencyHandler.livedata() = implementation("androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE")
fun KotlinDependencyHandler.datastore() = implementation("androidx.datastore:datastore-preferences:$DATASTORE")
fun KotlinDependencyHandler.androidxPreference() = implementation("androidx.preference:preference-ktx:$ANDROIDX_PREFERENCE")
fun KotlinDependencyHandler.androidCore() = implementation("androidx.core:core-ktx:$CORE")
fun KotlinDependencyHandler.compose()
{
    implementation("androidx.compose.runtime:runtime:$COMPOSE")
}

fun KotlinDependencyHandler.composeTest()
{
    implementation("androidx.compose.ui:ui-test-junit4:$COMPOSE")
    implementation("androidx.compose.ui:ui-test-manifest:$COMPOSE")
}

/**
 * Sets up common dependencies.
 */
fun NamedDomainObjectContainer<KotlinSourceSet>.commonMain(block: KotlinDependencyHandler.() -> Unit = {})
{
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
    compileSdkVersion(30)
    sourceSets.getByName("main").manifest.srcFile(manifestPath)
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = COMPOSE
    }
    buildFeatures {
        compose = true
    }
    block(this)
}

/**
 * Sets up Kotlin multiplatform targets.
 */
private fun KotlinMultiplatformExtension.targets()
{
    jvm()
    android {
        publishLibraryVariants("release", "debug")
    }
}

/**
 * Sets up Kotlin multiplatform.
 */
fun KotlinMultiplatformExtension.setup(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>.() -> Unit = {})
{
    targets()
    sourceSets(this.sourceSets)
}