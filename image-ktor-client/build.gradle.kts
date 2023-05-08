import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Fetching image content via Ktor.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktor.client)
        implementation(projects.logging)
        api(projects.imageModel)
    }
}