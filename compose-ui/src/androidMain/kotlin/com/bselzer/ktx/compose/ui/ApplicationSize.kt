package com.bselzer.ktx.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

actual class SystemApplicationSize : ApplicationSize {
    override val current: DpSize
        @Composable
        get() = LocalContext.current.resources.configuration.run {
            DpSize(
                width = screenWidthDp.dp,
                height = screenHeightDp.dp
            )
        }

    override val minimum: DpSize
        @Composable
        get() = LocalContext.current.resources.configuration.run {
            DpSize(
                width = smallestScreenWidthDp.dp,
                height = 0.dp
            )
        }
}