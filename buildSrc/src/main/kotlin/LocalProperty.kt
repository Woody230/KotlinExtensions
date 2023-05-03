import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project

object LocalProperty {
    const val SONATYPE_USERNAME = "sonatype.username"
    const val SONATYPE_PASSWORD = "sonatype.password"
    const val SIGNING_KEY_ID = "signing.keyId"
    const val SIGNING_KEY = "signing.key"
    const val SIGNING_PASSWORD = "signing.password"
}

fun Project.hasLocalProperties(vararg names: String) = names.all { name -> hasLocalProperty(name) }
fun Project.hasLocalProperty(name: String): Boolean = localPropertyOrNull(name) != null
fun Project.localProperty(name: String): String = localPropertyOrNull(name) ?: throw NotImplementedError("Set the $name in local properties.")
fun Project.localPropertyOrNull(name: String): String? = gradleLocalProperties(rootDir).getProperty(name)
fun Project.localPropertyFileTextOrNull(name: String): String? {
    val path = project.localPropertyOrNull(name)
    return path?.let { project.file(path).readText() }
}

fun Project.localPropertyFileText(name: String): String = localPropertyFileTextOrNull(name) ?: throw NotImplementedError("Set the $name in local properties.")