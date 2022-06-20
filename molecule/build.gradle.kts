plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

// This module is a copy of https://github.com/cashapp/molecule with minor modifications to make it usable for multiplatform.
publishing.publish(
    project = project,
    description = "A copy of molecule v0.2.0 with multiplatform capability."
) {
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