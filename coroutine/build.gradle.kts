publishConvention.description.set("coroutine extensions")

kotlin.sourceSets.commonMain {
    api(libs.ktx.coroutines)
}