plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.mobile.multiplatform-resources") version RESOURCE
}

buildscript {
    dependencies {
        classpath("dev.icerock.moko:resources-generator:$RESOURCE")
    }
}

publishing.publish(project)

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

multiplatformResources {
    multiplatformResourcesPackage = "com.bselzer.ktx.resource"
    multiplatformResourcesClassName = "KtxResources"
}