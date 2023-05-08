publishConvention.description.set("Storing image content via Kodein-DB.")

kotlin.sourceSets.commonMain {
    api(projects.kodeinDb)
    api(projects.imageModel)
}