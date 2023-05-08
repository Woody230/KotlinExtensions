import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Determine what connection capabilities exist using Ktor.")
}

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

kotlinMultiplatformDependencies {
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