package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal containing the [SnackbarHostState] for sending notifications.
 */
val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> = compositionLocalOf { SnackbarHostState() }