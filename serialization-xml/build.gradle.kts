plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("kotlinx.serialization extensions for pdvrieze.xmlutil")

kotlin.sourceSets.commonMain {
    api(libs.xml.serialization)
    implementation(projects.logging)
}