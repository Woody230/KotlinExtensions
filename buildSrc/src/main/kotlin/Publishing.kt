import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension

/**
 * Sets up the pom.xml that gets created using the maven-publish plugin.
 *
 * @see <a href="https://docs.gradle.org/current/userguide/publishing_customization.html">gradle</a>
 * @see <a href="https://maven.apache.org/pom.html">pom.xml</a>
 */
fun PublishingExtension.publish(project: Project, devs: MavenPomDeveloperSpec.() -> Unit = {}) = project.afterEvaluate {
    val releaseType = releaseType(Metadata.VERSION)
    repositories {
        maven {
            name = "sonatype"
            url(releaseType)
            signing(project)
        }
    }

    publications.withType<MavenPublication>().configureEach {
        artifact(project.extra.get("jar"))

        pom {
            name(project)
            description()
            licenses()
            developers(devs = devs)
            scm()
        }
    }
}

private enum class ReleaseType {
    RELEASE,
    SNAPSHOT;

    fun isRelease(): Boolean = this == RELEASE
}

/**
 * If the version matches semantic versioning then assume it to be a release build. Otherwise consider it a snapshot.
 */
private fun releaseType(version: String): ReleaseType {
    val isRelease = Regex("^(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\$").matches(version)
    return if (isRelease) ReleaseType.RELEASE else ReleaseType.SNAPSHOT
}

private fun MavenArtifactRepository.url(releaseType: ReleaseType) {
    val url = when (releaseType.isRelease()) {
        true -> "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
        false -> "https://s01.oss.sonatype.org/content/repositories/snapshots/"
    }

    setUrl(url)
}

private fun MavenArtifactRepository.signing(project: Project) {
    credentials {
        username = project.localPropertyOrNull(LocalProperty.SONATYPE_USERNAME)
        password = project.localPropertyOrNull(LocalProperty.SONATYPE_PASSWORD)
    }

    project.afterEvaluate {
        if (hasLocalProperties(LocalProperty.SIGNING_KEY, LocalProperty.SIGNING_PASSWORD)) {
            extensions.findByType<SigningExtension>()?.apply {
                val publishing = project.extensions.findByType<PublishingExtension>() ?: return@apply
                val keyId = localProperty(LocalProperty.SIGNING_KEY_ID)
                val key = localPropertyFileText(LocalProperty.SIGNING_KEY)
                val password = localProperty(LocalProperty.SIGNING_PASSWORD)

                useInMemoryPgpKeys(keyId, key, password)
                sign(publishing.publications)
            }
        }
    }
}

private fun MavenPom.name(project: Project) = name.set("${Metadata.SUBGROUP_ID}-${project.name}")
private fun MavenPom.description() = description.set("Extensions for the Kotlin standard library and third-party libraries.")
private fun MavenPom.licenses() = licenses {
    license {
        this.name.set("The Apache Software License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("repo")
    }
}

private fun MavenPom.developers(devs: MavenPomDeveloperSpec.() -> Unit = {}) = developers {
    devs()
    developer {
        id.set("Woody230")
        name.set("Brandon Selzer")
        email.set("bselzer1@outlook.com")
    }
}

private fun MavenPom.scm() {
    val repo = "https://github.com/Woody230/KotlinExtensions"
    url.set(repo)
    scm { url.set(repo) }
}
