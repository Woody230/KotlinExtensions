plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin:1.5.31"))
    implementation("com.android.tools.build:gradle:7.0.2")
}