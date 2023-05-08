import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

publishConvention {
    description.set("General Kotlin standard library extensions.")
}

kotlinMultiplatformDependencies {
    androidMain {
        api(libs.androidx.core.ktx)
    }
}