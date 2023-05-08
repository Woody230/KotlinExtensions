import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Based on Android intents.")
}

kotlinMultiplatformDependencies {
    commonMain {
        implementation(projects.logging)
    }
}