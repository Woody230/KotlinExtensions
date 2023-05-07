publish(
    description = "Extensions for base64 encoding and decoding between strings and byte arrays."
) {
    developer {
        name.set("jershell")
    }
}

android.setup(project)

kotlin.setup {
    commonTest {
        implementation(projects.function)
    }
}