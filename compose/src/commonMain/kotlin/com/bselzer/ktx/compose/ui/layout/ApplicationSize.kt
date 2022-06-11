package com.bselzer.ktx.compose.ui.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize

interface ApplicationSize {
    val current: DpSize
        @Composable
        get

    val minimum: DpSize
        @Composable
        get

    companion object : ApplicationSize by SystemApplicationSize()
}

expect class SystemApplicationSize() : ApplicationSize