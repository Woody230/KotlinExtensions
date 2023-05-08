import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("Logging wrapper around Napier.")
}

kotlinMultiplatformDependencies {
    commonMain {
        implementation(libs.napier)
    }
}