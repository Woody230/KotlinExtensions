import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Storing image content via Kodein-DB.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(projects.kodeinDb)
        api(projects.imageModel)
    }
}