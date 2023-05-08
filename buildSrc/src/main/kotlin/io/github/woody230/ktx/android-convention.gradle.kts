package io.github.woody230.ktx

import com.android.build.gradle.LibraryExtension
import io.github.woody230.ktx.Metadata.COMPILE_SDK
import io.github.woody230.ktx.Metadata.JAVA_VERSION
import io.github.woody230.ktx.Metadata.MIN_SDK
import io.github.woody230.ktx.Metadata.NAMESPACE_ID
import io.github.woody230.ktx.Metadata.SUBGROUP_ID
import gradle.kotlin.dsl.accessors._80a2ae57395e1362b61311ead0eb480f.android

// TODO can't access libs from precompiled scripts https://github.com/gradle/gradle/issues/15383
plugins {
    id("com.android.library")
}

with (android) {
    namespace = "${NAMESPACE_ID}.${SUBGROUP_ID}.${project.name}".replace("-", ".")
    compileSdk = COMPILE_SDK
    defaultConfig {
        minSdk = MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JAVA_VERSION
        targetCompatibility = JAVA_VERSION
    }
    testOptions {
        unitTests {
            androidResources {
                isIncludeAndroidResources = true
            }
        }
    }

    if (plugins.hasPlugin(libs.plugins.compose.get().pluginId)) {
        configureCompose()
    }
}

fun LibraryExtension.configureCompose() {
    buildFeatures {
        compose = true
    }
    composeOptions {
        // https://mvnrepository.com/artifact/org.jetbrains.compose.compiler/compiler
        // https://github.com/JetBrains/compose-multiplatform/blob/master/gradle-plugins/compose/src/main/kotlin/org/jetbrains/compose/ComposeCompilerCompatibility.kt
        kotlinCompilerExtensionVersion = libs.versions.multiplatform.compose.compiler.get()
    }

    packaging {
        resources.pickFirsts.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }
}