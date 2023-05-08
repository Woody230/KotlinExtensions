publishConvention.description.set("AboutLibraries extensions")

kotlin.sourceSets.commonMain {
    api(libs.about.libraries)
}