publish(
    description = "Determine what connection capabilities exist using Ktor."
)

android.setup(project)

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

kotlin.setup {
    commonMain {
        api(libs.ktor.client)
        implementation(projects.intent)
    }
    commonTest {
        implementation(libs.ktx.coroutines)
        implementation(libs.ktor.client.mock)
    }
    androidUnitTest {
        implementation(libs.ktor.client.okhttp)
    }
    jvmTest {
        implementation(libs.ktor.client.okhttp)
    }
}