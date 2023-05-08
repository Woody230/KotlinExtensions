import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Client side Ktor extensions.")
}

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

kotlinMultiplatformDependencies {
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