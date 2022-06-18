import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project

fun Project.hasLocalProperties(vararg names: String) = names.all { name -> hasLocalProperty(name) }
fun Project.hasLocalProperty(name: String): Boolean = localPropertyOrNull(name) != null
fun Project.localProperty(name: String): String = localPropertyOrNull(name) ?: throw NotImplementedError("Set the $name in local properties.")
fun Project.localPropertyOrNull(name: String): String? = gradleLocalProperties(rootDir).getProperty(name)
fun Project.localPropertyFileTextOrNull(name: String): String? {
    val path = project.localPropertyOrNull(name)
    return path?.let { project.file(path).readText() }
}

fun Project.localPropertyFileText(name: String): String = localPropertyFileTextOrNull(name) ?: throw NotImplementedError("Set the $name in local properties.")