plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Code generation using Square's kotlinpoet."
)

android.setup()

kotlin.setup {
    commonMain {
        projectCodeGenModel()
    }
    commonTest()
    jvmMain {
        poet()
    }
}