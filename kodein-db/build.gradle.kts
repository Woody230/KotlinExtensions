publishConvention.description.set("Kodein-DB extensions.")

kotlin.sourceSets.commonMain {
    api(libs.kodein.db)
    api(projects.valueIdentifier)
}