plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    sourceSets {
        val androidMain by getting {
            dependencies {
                // TODO common
                implementation("com.squareup.moshi:moshi-kotlin:1.11.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
    }
}

android {
    compileSdkVersion(30)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(23)
        targetSdkVersion(30)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}