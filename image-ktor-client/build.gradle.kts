publishConvention.description.set("Fetching image content via Ktor.")

kotlin.sourceSets.commonMain {
    api(libs.ktor.client)
    implementation(projects.logging)
    api(projects.imageModel)
}