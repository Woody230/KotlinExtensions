publish(
    description = "Object comparators."
)

android.setup(project)

kotlin.setup {
    commonMain {
        implementation(projects.function)
    }
}