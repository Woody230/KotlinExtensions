publish(
    description = "General Kotlin standard library extensions."
)

android.setup(project)

kotlin.setup {
    androidMain {
        api(libs.androidx.core.ktx)
    }
}