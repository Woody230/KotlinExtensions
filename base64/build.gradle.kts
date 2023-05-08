publishConvention.apply {
    description.set("Extensions for base64 encoding and decoding between strings and byte arrays.")
    devs.set {
        developer {
            name.set("jershell")
        }
    }
}

kotlin.sourceSets.commonTest {
    implementation(projects.function)
}