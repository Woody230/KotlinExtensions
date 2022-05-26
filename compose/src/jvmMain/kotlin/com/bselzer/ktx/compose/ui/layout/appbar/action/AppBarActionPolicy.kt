package com.bselzer.ktx.compose.ui.layout.appbar.action

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import com.bselzer.ktx.compose.ui.layout.window.LocalWindowState

internal actual class SystemActionPolicy : AdjustableActionPolicy() {
    // TODO adaptive size https://github.com/JetBrains/compose-jb/issues/986
    override val applicationSize: DpSize
        @Composable
        get() = LocalWindowState.current.size
}