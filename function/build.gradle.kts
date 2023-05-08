publishConvention.description.set("General Kotlin standard library extensions.")

kotlin.sourceSets.androidMain {
    api(libs.androidx.core.ktx)
}