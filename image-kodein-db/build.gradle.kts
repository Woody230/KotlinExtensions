publish(
    description = "Storing image content via Kodein-DB."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(projects.kodeinDb)
        api(projects.imageModel)
    }
}