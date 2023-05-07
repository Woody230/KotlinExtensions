plugins {
    alias(libs.plugins.ktx.serialization)
}

publish(
    description = "kotlinx.serialization extensions for pdvrieze.xmlutil"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.xml.serialization)
        implementation(projects.logging)
    }
}