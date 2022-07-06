package com.bselzer.ktx.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize

actual class SystemApplicationSize : ApplicationSize {
    // TODO adaptive size https://github.com/JetBrains/compose-jb/issues/986
    override val current: DpSize
        @Composable
        get() = LocalWindowState.current.size

    override val minimum: DpSize
        @Composable
        get() = DpSize.Zero
}