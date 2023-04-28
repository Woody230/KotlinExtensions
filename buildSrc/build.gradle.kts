plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin:1.8.10"))
    implementation("com.android.tools.build:gradle:7.4.2")
}