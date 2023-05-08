plugins {
    alias(libs.plugins.ktx.serialization)
}

publishConvention.description.set("Image models.")

kotlin.sourceSets.commonMain {
    api(libs.ktx.serialization.core)
}
