package com.bselzer.ktx.compose.ui.notification.snackbar

import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * CompositionLocal containing the [SnackbarHostState] for sending notifications.
 */
val LocalSnackbarHostState: ProvidableCompositionLocal<SnackbarHostState> = compositionLocalOf { SnackbarHostState() }

/**
 * Displays a snackbar notification with the given [message] and button [actionLabel] for the given [duration].
 */
@Composable
fun ShowSnackbar(
    message: String,
    actionLabel: String? = null,
    duration: SnackbarDuration = SnackbarDuration.Short,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    val state = LocalSnackbarHostState.current
    scope.launch {
        state.showSnackbar(message, actionLabel, duration)
    }
}