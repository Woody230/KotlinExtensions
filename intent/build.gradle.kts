publish(
    description = "Based on Android intents."
)

android.setup(project)

kotlin.setup {
    commonMain {
        implementation(projects.logging)
    }
}