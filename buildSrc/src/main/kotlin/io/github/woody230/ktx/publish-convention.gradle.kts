package io.github.woody230.ktx

import io.github.woody230.ktx.Metadata.GROUP_ID
import io.github.woody230.ktx.Metadata.SUBGROUP_ID
import io.github.woody230.ktx.Metadata.VERSION
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
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

interface PublishExtension {
    val description: Property<String>
    val devs: Property<MavenPomDeveloperSpec.() -> Unit>
}

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.vanniktech.maven.publish.base")
    id("org.jetbrains.dokka")
}

val extension = extensions.create<PublishExtension>("publishExtension")
extension.devs.convention { }

afterEvaluate {
    val extension = extensions.getByType<PublishExtension>()
    with(extensions.getByType<MavenPublishBaseExtension>()) {
        setupGradleProperties()

        coordinates("$GROUP_ID.$SUBGROUP_ID", name, VERSION)
        pom {
            configure(project, extension.description.get(), extension.devs.get())
        }

        configureMultiplatform()
        publish()

        if (hasLocalProperties(LocalProperty.SIGNING_KEY, LocalProperty.SIGNING_PASSWORD)) {
            signAllPublications()
        }
    }
}

fun MavenPublishBaseExtension.configureMultiplatform() {
    val jar = JavadocJar.Dokka("dokkaHtml")
    val platform = KotlinMultiplatform(javadocJar = jar)
    configure(platform)
}

fun MavenPublishBaseExtension.publish() = publishToMavenCentral(
    host = SonatypeHost.S01,
    automaticRelease = false
)

fun MavenPom.configure(
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

fun Project.setupGradleProperties() = with(extraProperties) {
    set(ExtraProperty.MAVEN_CENTRAL_USERNAME, localPropertyOrNull(LocalProperty.SONATYPE_USERNAME))
    set(ExtraProperty.MAVEN_CENTRAL_PASSWORD, localPropertyOrNull(LocalProperty.SONATYPE_PASSWORD))

    if (hasLocalProperties(LocalProperty.SIGNING_KEY, LocalProperty.SIGNING_PASSWORD)) {
        set(ExtraProperty.SIGNING_KEY_ID, localProperty(LocalProperty.SIGNING_KEY_ID))
        set(ExtraProperty.SIGNING_KEY, localPropertyFileText(LocalProperty.SIGNING_KEY))
        set(ExtraProperty.SIGNING_PASSWORD, localProperty(LocalProperty.SIGNING_PASSWORD))
    }
}

fun MavenPom.licenses() = licenses {
    license {
        this.name.set("The Apache Software License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("repo")
    }
}

fun MavenPom.developers(devs: MavenPomDeveloperSpec.() -> Unit = {}) = developers {
    devs()
    developer {
        id.set("Woody230")
        name.set("Brandon Selzer")
        email.set("bselzer1@outlook.com")
    }
}

fun MavenPom.scm() {
    val repo = "https://github.com/Woody230/KotlinExtensions"
    url.set(repo)
    scm { url.set(repo) }
}