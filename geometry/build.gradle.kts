plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

publishing.publish(
    project = project,
    description = "Two and three dimensional geometrical objects."
)

android.setup(project)

kotlin.setup {
    commonMain()
    commonTest()
    androidUnitTest()
    jvmTest()
}