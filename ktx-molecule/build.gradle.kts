plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

// This module is a copy of https://github.com/cashapp/molecule with minor modifications to make it usable for multiplatform.
publishing.publish(project) {
    developer {
        name.set("CashApp")
    }
}

android.setupWithCompose()

kotlin.setup {
    commonMain {
        runtime()
        coroutine()
    }
    commonTest()
}