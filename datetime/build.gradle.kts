plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
}

publishing.publish(
    project = project,
    description = "kotlinx.datetime extensions"
)

android.setup(project) {
    compileOptions {
        // DateTimeFormatter requires API level 26+ otherwise
        isCoreLibraryDesugaringEnabled = true
    }
    dependencies {
        coreLibraryDesugaring(libs.android.desugar)
    }
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktx.datetime)
        api(libs.ktx.coroutines)
    }
    commonTest {
        implementation(libs.bundles.common.test)
        projectIntl()
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
    }
}