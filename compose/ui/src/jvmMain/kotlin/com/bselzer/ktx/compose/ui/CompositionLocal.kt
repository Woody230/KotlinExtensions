package com.bselzer.ktx.compose.ui

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.window.WindowState

/**
 * Provides the current window state.
 */
val LocalWindowState: ProvidableCompositionLocal<WindowState> = compositionLocalOf { WindowState() }