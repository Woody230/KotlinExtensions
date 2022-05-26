package com.bselzer.ktx.compose.ui.layout.appbar.action

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

internal actual class SystemActionPolicy : AdjustableActionPolicy() {
    override val applicationSize: DpSize
        @Composable
        get() = LocalContext.current.resources.configuration.run {
            DpSize(
                width = screenWidthDp.dp,
                height = screenHeightDp.dp
            )
        }

    override val minimumApplicationSize: DpSize
        @Composable
        get() = LocalContext.current.resources.configuration.run {
            DpSize(
                width = smallestScreenWidthDp.dp,
                height = Dp.Unspecified
            )
        }
}