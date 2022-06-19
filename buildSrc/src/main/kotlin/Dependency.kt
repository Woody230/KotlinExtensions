import Versions.ANDROIDX_PREFERENCE
import Versions.COMPOSE
import Versions.CORE
import Versions.COROUTINE
import Versions.DATASTORE
import Versions.KODEIN_DB
import Versions.KTOR
import Versions.KTX_DATETIME
import Versions.KTX_SERIALIZATION
import Versions.LICENSING
import Versions.LIFECYCLE
import Versions.LOGGING
import Versions.MOSHI
import Versions.RESOURCE
import Versions.SETTINGS
import Versions.XML
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Versions {
    const val KTX_DATETIME = "0.3.2"
    const val KTX_SERIALIZATION = "1.3.2"
    const val MOSHI = "1.11.0"
    const val LIFECYCLE = "2.3.1"
    const val CORE = "1.7.0"
    const val ANDROID_TEST = "1.1.0"
    const val ROBOLECTRIC = "4.6.1"
    const val DATASTORE = "1.0.0"
    const val ANDROIDX_PREFERENCE = "1.1.1"
    const val KODEIN_DB = "0.9.0-beta"
    const val SETTINGS = "0.8.1"
    const val COROUTINE = "1.6.1"
    const val LOGGING = "2.4.0"
    const val LICENSING = "10.0.0"
    const val KTOR = "2.0.1"
    const val ANDROID_COMPOSE = "1.1.1"
    const val XML = "0.84.0"
    const val RESOURCE = "0.19.0"
    const val COMPOSE = "1.1.0"
    const val KOTLIN = "1.6.10"
}

object Metadata {
    const val GROUP_ID = "io.github.woody230"
    const val VERSION = "4.0.1"
    const val JVM_TARGET = "1.8"
}

object LocalProperty {
    const val SONATYPE_USERNAME = "sonatype.username"
    const val SONATYPE_PASSWORD = "sonatype.password"
    const val SIGNING_KEY_ID = "signing.keyId"
    const val SIGNING_KEY = "signing.key"
    const val SIGNING_PASSWORD = "signing.password"
}

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

fun KotlinDependencyHandler.xmlSerialization() = api("io.github.pdvrieze.xmlutil:serialization:$XML")

fun KotlinDependencyHandler.resources() = api("dev.icerock.moko:resources:$RESOURCE")
fun KotlinDependencyHandler.resourcesCompose() = api("dev.icerock.moko:resources-compose:$RESOURCE")

fun KotlinDependencyHandler.logging() = implementation("io.github.aakira:napier:$LOGGING")
fun KotlinDependencyHandler.licensing() = api("com.mikepenz:aboutlibraries-core:${LICENSING}")

fun KotlinDependencyHandler.ktorClient() = api("io.ktor:ktor-client-core:$KTOR")
fun KotlinDependencyHandler.ktorOkHttpEngine() = implementation("io.ktor:ktor-client-okhttp:$KTOR")
fun KotlinDependencyHandler.ktorMockEngine() = implementation("io.ktor:ktor-client-mock:$KTOR")

fun KotlinDependencyHandler.runtime() = api("org.jetbrains.compose.runtime:runtime:$COMPOSE")
fun KotlinDependencyHandler.ui() = api("org.jetbrains.compose.ui:ui:$COMPOSE")
fun KotlinDependencyHandler.uiUtil() = api("org.jetbrains.compose.ui:ui-util:$COMPOSE")
fun KotlinDependencyHandler.material() = api("org.jetbrains.compose.material:material:$COMPOSE")
fun KotlinDependencyHandler.foundation() = api("org.jetbrains.compose.foundation:foundation:$COMPOSE")

fun KotlinDependencyHandler.projectConstraintLayout() = api(project(":ktx-compose-constraint-layout"))
fun KotlinDependencyHandler.projectFunction() = api(project(":ktx-function"))
fun KotlinDependencyHandler.projectSettings() = api(project(":ktx-settings"))
fun KotlinDependencyHandler.projectCompose() = api(project(":ktx-compose"))
fun KotlinDependencyHandler.projectComposeResource() = api(project(":ktx-compose-resource"))
fun KotlinDependencyHandler.projectKodeinDb() = api(project(":ktx-kodein-db"))
fun KotlinDependencyHandler.projectResource() = api(project(":ktx-resource"))
fun KotlinDependencyHandler.projectIntent() = api(project(":ktx-intent"))
fun KotlinDependencyHandler.projectLogging() = implementation(project(":ktx-logging"))
fun KotlinDependencyHandler.projectValue() = api(project(":ktx-value"))
fun KotlinDependencyHandler.projectIntl() = api(project(":ktx-intl"))
fun KotlinDependencyHandler.projectSerializationCompose() = api(project(":ktx-serialization-compose"))