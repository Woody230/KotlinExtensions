publish(
    description = "kotlinx.datetime extensions"
)

android.setup(project) {
    // DateTimeFormatter requires API level 26+ otherwise
    setupDesugaring(project, libs.android.desugar)
}

kotlin.setup {
    commonMain {
        api(libs.ktx.datetime)
        api(libs.ktx.coroutines)
    }
    commonTest {
        implementation(projects.intl)
    }
}