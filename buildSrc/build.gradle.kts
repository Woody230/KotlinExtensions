plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin:1.6.10"))
    implementation("com.android.tools.build:gradle:7.0.4")
}