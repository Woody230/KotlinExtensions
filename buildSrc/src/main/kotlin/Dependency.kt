import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Versions {
    const val COMPOSE_COMPILER = "1.4.2"
}

object Metadata {
    const val GROUP_ID = "io.github.woody230"
    const val SUBGROUP_ID = "ktx"
    const val NAMESPACE_ID = "com.bselzer"
    const val VERSION = "5.3.1"
    const val JVM_TARGET = "11"
}

object LocalProperty {
    const val SONATYPE_USERNAME = "sonatype.username"
    const val SONATYPE_PASSWORD = "sonatype.password"
    const val SIGNING_KEY_ID = "signing.keyId"
    const val SIGNING_KEY = "signing.key"
    const val SIGNING_PASSWORD = "signing.password"
}

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
