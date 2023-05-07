publish(
    description = "Logging wrapper around Napier."
)

android.setup(project)

kotlin.setup {
    commonMain {
        implementation(libs.napier)
    }
}