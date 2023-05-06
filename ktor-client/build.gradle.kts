plugins {
    id(libs.plugins.multiplatform.get().pluginId)
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.publish.get().pluginId)
}

publish(
    description = "Client side Ktor extensions."
)

android.setup(project)

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

kotlin.setup {
    commonMain {
        api(libs.bundles.common)
        api(libs.ktor.client)
    }
    commonTest {
        implementation(libs.bundles.common.test)
        implementation(libs.ktx.coroutines)
        implementation(libs.ktor.client.mock)
    }
    androidUnitTest {
        implementation(libs.bundles.android.unit.test)
        implementation(libs.ktor.client.okhttp)
    }
    jvmTest {
        implementation(libs.bundles.jvm.test)
        implementation(libs.ktor.client.okhttp)
    }
}