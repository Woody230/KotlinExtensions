plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    alias(libs.plugins.compose)
}

// This module is a copy of https://github.com/androidx/constraintlayout with minor modifications to make it usable for multiplatform.
publishing.publish(
    project = project,
    description = "A copy of Android's ConstraintLayout (v2.1.3 core and v1.0.0 compose) with multiplatform capability."
) {
    developer {
        name.set("The Android Open Source Project")
    }
}

android.setup(project) {
    setupCompose(libs.versions.multiplatform.compose.compiler)

    packaging {
        resources.pickFirsts.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.bundles.compose)
        api(libs.ktx.datetime)
    }
    commonTest {
        implementation(libs.bundles.common.test)
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
    }
}