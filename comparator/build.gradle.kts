import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Object comparators.")
}

kotlinMultiplatformDependencies {
    commonMain {
        implementation(projects.function)
    }
}