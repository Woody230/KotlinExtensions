import Versions.ABOUT_LIBRARIES
import Versions.COMPOSE
import Versions.CORE
import Versions.COROUTINE
import Versions.KODEIN_DB
import Versions.KTOR
import Versions.KTX_DATETIME
import Versions.KTX_SERIALIZATION
import Versions.NAPIER
import Versions.RESOURCE
import Versions.SETTINGS
import Versions.XML
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Versions {
    const val KTX_DATETIME = "0.4.0"
    const val KTX_SERIALIZATION = "1.5.0"
    const val CORE = "1.10.0"
    const val ANDROID_TEST_CORE = "1.5.0"
    const val ANDROID_TEST_JUNIT = "1.1.5"
    const val ANDROID_TEST_RUNNER = "1.5.2"
    const val ANDROID_TEST_RULES = "1.5.0"
    const val ROBOLECTRIC = "4.10"
    const val KODEIN_DB = "0.9.0-beta"
    const val SETTINGS = "1.0.0"
    const val COROUTINE = "1.6.4"
    const val NAPIER = "2.6.1"
    const val ABOUT_LIBRARIES = "10.6.2"
    const val KTOR = "2.3.0"
    const val ANDROID_COMPOSE = "1.4.2"
    const val XML = "0.85.0"
    const val RESOURCE = "0.22.0"
    const val COMPOSE = "1.3.1"
    const val COMPOSE_COMPILER = "1.4.2"
    const val KOTLIN = "1.8.10"
}

object Metadata {
    const val GROUP_ID = "io.github.woody230"
    const val SUBGROUP_ID = "ktx"
    const val NAMESPACE_ID = "com.bselzer.woody230.$SUBGROUP_ID"
    const val VERSION = "5.3.0"
    const val JVM_TARGET = "11"
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

fun KotlinDependencyHandler.napier() = implementation("io.github.aakira:napier:$NAPIER")
fun KotlinDependencyHandler.aboutLibraries() = api("com.mikepenz:aboutlibraries-core:${ABOUT_LIBRARIES}")

fun KotlinDependencyHandler.ktorClient() = api("io.ktor:ktor-client-core:$KTOR")
fun KotlinDependencyHandler.ktorOkHttpEngine() = implementation("io.ktor:ktor-client-okhttp:$KTOR")
fun KotlinDependencyHandler.ktorMockEngine() = implementation("io.ktor:ktor-client-mock:$KTOR")

fun KotlinDependencyHandler.runtime() = api("org.jetbrains.compose.runtime:runtime:$COMPOSE")
fun KotlinDependencyHandler.ui() = api("org.jetbrains.compose.ui:ui:$COMPOSE")
fun KotlinDependencyHandler.uiUtil() = api("org.jetbrains.compose.ui:ui-util:$COMPOSE")
fun KotlinDependencyHandler.material() = api("org.jetbrains.compose.material:material:$COMPOSE")
fun KotlinDependencyHandler.foundation() = api("org.jetbrains.compose.foundation:foundation:$COMPOSE")

fun KotlinDependencyHandler.projectAboutLibraries() = api(project(":aboutlibraries"))
fun KotlinDependencyHandler.projectConstraintLayout() = api(project(":compose-constraint-layout"))
fun KotlinDependencyHandler.projectDateTime() = api(project(":datetime"))
fun KotlinDependencyHandler.projectFunction() = api(project(":function"))
fun KotlinDependencyHandler.projectSettings() = api(project(":settings"))
fun KotlinDependencyHandler.projectComposeUi() = api(project(":compose-ui"))
fun KotlinDependencyHandler.projectComposeUiIntl() = api(project(":compose-ui-intl"))
fun KotlinDependencyHandler.projectComposeUiLayout() = api(project(":compose-ui-layout"))
fun KotlinDependencyHandler.projectComposeUiLayoutCommon() = api(project(":compose-ui-layout-common"))
fun KotlinDependencyHandler.projectComposeUiLayoutCustom() = api(project(":compose-ui-layout-custom"))
fun KotlinDependencyHandler.projectComposeUiGraphics() = api(project(":compose-ui-graphics"))
fun KotlinDependencyHandler.projectComposeResource() = api(project(":compose-resource"))
fun KotlinDependencyHandler.projectComposeSerialization() = api(project(":compose-serialization"))
fun KotlinDependencyHandler.projectKodeinDb() = api(project(":kodein-db"))
fun KotlinDependencyHandler.projectResource() = api(project(":resource"))
fun KotlinDependencyHandler.projectIntent() = api(project(":intent"))
fun KotlinDependencyHandler.projectLogging() = implementation(project(":logging"))
fun KotlinDependencyHandler.projectValueIdentifier() = api(project(":value-identifier"))
fun KotlinDependencyHandler.projectIntl() = api(project(":intl"))
fun KotlinDependencyHandler.projectImageModel() = api(project(":image-model"))
fun KotlinDependencyHandler.projectGeometry() = api(project(":geometry"))
fun KotlinDependencyHandler.projectSerialization() = api(project(":serialization"))
