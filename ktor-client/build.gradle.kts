publishConvention.description.set("Client side Ktor extensions.")

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

kotlin.sourceSets.apply {
    commonMain {
        api(libs.ktor.client)
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