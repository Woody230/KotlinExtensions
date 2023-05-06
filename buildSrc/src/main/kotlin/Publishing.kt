import Metadata.GROUP_ID
import Metadata.SUBGROUP_ID
import Metadata.VERSION
import com.vanniktech.maven.publish.AndroidMultiVariantLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPomDeveloperSpec
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.kotlin.gradle.plugin.extraProperties

private object LocalProperty {
    const val SONATYPE_USERNAME = "sonatype.username"
    const val SONATYPE_PASSWORD = "sonatype.password"
    const val SIGNING_KEY_ID = "signing.keyId"
    const val SIGNING_KEY = "signing.key"
    const val SIGNING_PASSWORD = "signing.password"
}

private object ExtraProperty {
    const val MAVEN_CENTRAL_USERNAME = "mavenCentralUsername"
    const val MAVEN_CENTRAL_PASSWORD = "mavenCentralPassword"
    const val SIGNING_KEY_ID = "signingInMemoryKeyId"
    const val SIGNING_KEY = "signingInMemoryKey"
    const val SIGNING_PASSWORD = "signingInMemoryKeyPassword"
}

fun Project.publish(
    description: String,
    devs: MavenPomDeveloperSpec.() -> Unit = {}
) = with(extensions.getByType<MavenPublishBaseExtension>()) {
    setupGradleProperties()

    coordinates("$GROUP_ID.$SUBGROUP_ID", name, VERSION)
    pom {
        configure(project, description, devs)
    }

    configureMultiplatform()
    publish()

    if (hasLocalProperties(LocalProperty.SIGNING_KEY, LocalProperty.SIGNING_PASSWORD)) {
        signAllPublications()
    }
}

private fun MavenPublishBaseExtension.configureMultiplatform() {
    val jar = JavadocJar.Dokka("dokkaHtml")
    val platform = KotlinMultiplatform(javadocJar = jar)
    configure(platform)
}

private fun MavenPublishBaseExtension.publish() = publishToMavenCentral(
    host = SonatypeHost.S01,
    automaticRelease = false
)

private fun MavenPom.configure(
    project: Project,
    description: String,
    devs: MavenPomDeveloperSpec.() -> Unit = {}
) {
    this.name.set("${Metadata.SUBGROUP_ID}-${project.name}")
    this.description.set(description)
    licenses()
    developers(devs = devs)
    scm()
}

private fun Project.setupGradleProperties() = with(extraProperties) {
    set(ExtraProperty.MAVEN_CENTRAL_USERNAME, localPropertyOrNull(LocalProperty.SONATYPE_USERNAME))
    set(ExtraProperty.MAVEN_CENTRAL_PASSWORD, localPropertyOrNull(LocalProperty.SONATYPE_PASSWORD))

    if (hasLocalProperties(LocalProperty.SIGNING_KEY, LocalProperty.SIGNING_PASSWORD)) {
        set(ExtraProperty.SIGNING_KEY_ID, localProperty(LocalProperty.SIGNING_KEY_ID))
        set(ExtraProperty.SIGNING_KEY, localPropertyFileText(LocalProperty.SIGNING_KEY))
        set(ExtraProperty.SIGNING_PASSWORD, localProperty(LocalProperty.SIGNING_PASSWORD))
    }
}

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
