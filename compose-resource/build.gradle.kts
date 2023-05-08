plugins {
    alias(libs.plugins.compose)
}

publishConvention.description.set("Wrappers for strings and images using the compose and resource modules.")

kotlin.sourceSets.commonMain {
    api(projects.resource)
    api(projects.composeUiLayoutCommon)
    api(projects.composeUiIntl)
}