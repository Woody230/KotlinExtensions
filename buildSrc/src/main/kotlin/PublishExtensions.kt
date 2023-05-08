import org.gradle.api.Project

val Project.publishConvention: io.github.woody230.ktx.Publish_convention_gradle.PublishExtension
    get() = extensions.getByName("publishExtension") as io.github.woody230.ktx.Publish_convention_gradle.PublishExtension