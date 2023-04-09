import Versions.ABOUT_LIBRARIES
import Versions.COMPOSE
import Versions.CORE
import Versions.COROUTINE
import Versions.KODEIN_DB
import Versions.KTOR
import Versions.KTX_DATETIME
import Versions.KTX_SERIALIZATION
import Versions.NAPIER
import Versions.POET
import Versions.RESOURCE
import Versions.SETTINGS
import Versions.XML
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Versions {
    const val KTX_DATETIME = "0.3.2"
    const val KTX_SERIALIZATION = "1.3.2"
    const val CORE = "1.7.0"
    const val ANDROID_TEST = "1.1.0"
    const val ROBOLECTRIC = "4.6.1"
    const val KODEIN_DB = "0.9.0-beta"
    const val SETTINGS = "0.8.1"
    const val COROUTINE = "1.6.1"
    const val NAPIER = "2.4.0"
    const val ABOUT_LIBRARIES = "10.0.0"
    const val KTOR = "2.0.1"
    const val ANDROID_COMPOSE = "1.1.1"
    const val XML = "0.84.0"
    const val RESOURCE = "0.19.0"
    const val COMPOSE = "1.1.0"
    const val KOTLIN = "1.6.10"
    const val POET = "1.12.0"
}

object Metadata {
    const val GROUP_ID = "io.github.woody230"
    const val SUBGROUP_ID = "ktx"
    const val VERSION = "5.1.0"
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

fun KotlinDependencyHandler.androidCore() = api("androidx.core:core-ktx:$CORE")
fun KotlinDependencyHandler.kodeinDb() = api("org.kodein.db:kodein-db-api:$KODEIN_DB")
fun KotlinDependencyHandler.coroutine() = api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$COROUTINE")
fun KotlinDependencyHandler.multiplatformSettings() {
    api("com.russhwolf:multiplatform-settings:$SETTINGS")
    api("com.russhwolf:multiplatform-settings-coroutines:$SETTINGS")
}
fun KotlinDependencyHandler.poet() = api("com.squareup:kotlinpoet:$POET")

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
fun KotlinDependencyHandler.projectKodeinDb() = api(project(":kodein-db"))
fun KotlinDependencyHandler.projectResource() = api(project(":resource"))
fun KotlinDependencyHandler.projectIntent() = api(project(":intent"))
fun KotlinDependencyHandler.projectLogging() = implementation(project(":logging"))
fun KotlinDependencyHandler.projectSerialization() = api(project(":serialization"))
fun KotlinDependencyHandler.projectValue() = api(project(":value"))
fun KotlinDependencyHandler.projectIntl() = api(project(":intl"))
fun KotlinDependencyHandler.projectImageModel() = api(project(":image-model"))
fun KotlinDependencyHandler.projectGeometry() = api(project(":geometry"))
fun KotlinDependencyHandler.projectOpenApiModel() = api(project(":openapi-model"))
fun KotlinDependencyHandler.projectCodeGenModel() = api(project(":codegen-model"))
