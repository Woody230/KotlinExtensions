import com.bselzer.gradle.multiplatform.configure.sourceset.multiplatformDependencies

plugins {
    id(libs.plugins.woody230.ktx.convention.multiplatform.get().pluginId)
}

multiplatformPublishExtension {
    description.set("Determine what connection capabilities exist using Ktor.")
}

dependencies {
    testImplementation(project(mapOf("path" to ":intent")))
}

multiplatformDependencies {
    commonMain {
        api(libs.ktor.client)
        implementation(projects.intent)
    }
    commonTest {
        implementation(libs.ktx.coroutines)
        implementation(libs.ktor.client.mock)
    }
    androidUnitTest {
        implementation(libs.ktor.client.okhttp)
    }
    jvmTest {
        implementation(libs.ktor.client.okhttp)
    }
}