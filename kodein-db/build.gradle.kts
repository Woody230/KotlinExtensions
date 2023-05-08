import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Kodein-DB extensions.")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.kodein.db)
        api(projects.valueIdentifier)
    }
}