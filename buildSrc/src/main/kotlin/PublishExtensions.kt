import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPomDeveloper
import org.gradle.api.publish.maven.MavenPomDeveloperSpec

val Project.publishConvention: io.github.woody230.ktx.Publish_convention_gradle.PublishExtension
    get() = extensions.getByName("publishExtension") as io.github.woody230.ktx.Publish_convention_gradle.PublishExtension

fun Project.publishConvention(configure: io.github.woody230.ktx.Publish_convention_gradle.PublishExtension.() -> Unit) = publishConvention.apply(configure)