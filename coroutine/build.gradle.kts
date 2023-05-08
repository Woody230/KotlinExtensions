import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("coroutine extensions")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.coroutines)
    }
}