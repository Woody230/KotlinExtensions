import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("AboutLibraries extensions")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.about.libraries)
    }
}