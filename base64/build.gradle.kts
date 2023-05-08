import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Extensions for base64 encoding and decoding between strings and byte arrays.")
    developer {
        name.set("jershell")
    }
}

kotlinMultiplatformDependencies {
    commonTest {
        implementation(projects.function)
    }
}