publish(
    description = "coroutine extensions"
)

android.setup(project)

kotlin.setup {
    commonMain {
        api(libs.ktx.coroutines)
    }
}