plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("gradle-plugin:1.5.20"))
    implementation("com.android.tools.build:gradle:4.1.0")
}