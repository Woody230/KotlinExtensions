import Metadata.COMPILE_SDK
import Metadata.JAVA_VERSION
import Metadata.MIN_SDK
import Metadata.NAMESPACE_ID
import Metadata.SUBGROUP_ID

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
}