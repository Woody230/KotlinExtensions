import io.github.woody230.gradle.kotlin.multiplatform.kotlinMultiplatformDependencies

plugins {
    // DateTimeFormatter requires API level 26+ otherwise
    id(libs.plugins.woody230.android.desugar.get().pluginId)
}

publishConvention {
    description.set("kotlinx.datetime extensions")
}

kotlinMultiplatformDependencies {
    commonMain {
        api(libs.ktx.datetime)
        api(libs.ktx.coroutines)
    }
    commonTest {
        implementation(projects.intl)
    }
}