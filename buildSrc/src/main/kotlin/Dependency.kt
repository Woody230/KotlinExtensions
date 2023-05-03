import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

object Metadata {
    const val GROUP_ID = "io.github.woody230"
    const val SUBGROUP_ID = "ktx"
    const val NAMESPACE_ID = "com.bselzer"
    const val VERSION = "5.4.0"
    const val JVM_TARGET = "11"
    const val COMPILE_SDK = 33
    const val MIN_SDK = 21
}

object LocalProperty {
    const val SONATYPE_USERNAME = "sonatype.username"
    const val SONATYPE_PASSWORD = "sonatype.password"
    const val SIGNING_KEY_ID = "signing.keyId"
    const val SIGNING_KEY = "signing.key"
    const val SIGNING_PASSWORD = "signing.password"
}