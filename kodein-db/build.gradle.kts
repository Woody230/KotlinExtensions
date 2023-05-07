publish(
    description = "Kodein-DB extensions."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.kodein.db)
        api(projects.valueIdentifier)
    }
}