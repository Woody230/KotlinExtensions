publish(
    description = "AboutLibraries extensions."
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.about.libraries)
    }
}