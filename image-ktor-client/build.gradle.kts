publish(
    description = "Fetching image content via Ktor."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktor.client)
        implementation(projects.logging)
        api(projects.imageModel)
    }
}