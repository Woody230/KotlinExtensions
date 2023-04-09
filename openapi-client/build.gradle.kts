plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

publishing.publish(
    project = project,
    description = "OpenApi client model generation."
)

android.setup()

kotlin.setup {
    commonMain {
        ktxDateTime()
        projectOpenApiModel()
        projectValue()
        projectCodeGenModel()
    }
    commonTest()
    jvmMain {
        poet()
    }
}