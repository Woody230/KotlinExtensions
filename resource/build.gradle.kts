plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources") version Versions.RESOURCE
}

buildscript {
    dependencies {
        classpath("dev.icerock.moko:resources-generator:${Versions.RESOURCE}")
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.bselzer.ktx.resource"
    multiplatformResourcesClassName = "KtxResources"
}

publishing.publish(
    project = project,
    description = "moko-resources extensions"
)

android.setup()

kotlin.setup {
    commonMain {
        resources()
        ktxDateTime()
        projectIntent()
        projectIntl()
    }
    commonTest()
}