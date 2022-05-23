package com.bselzer.ktx.compose.ui.layout.snackbarhost

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.notification.snackbar.LocalSnackbarHostState

data class SnackbarHostInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * Component to host Snackbars that are pushed to be shown via SnackbarHostState.showSnackbar. Usually it's a SnackbarHost
     */
    val state: SnackbarHostState = SnackbarHostState(),
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = SnackbarHostInteractor()
    }
}

/**
 * Remembers the [SnackbarHostState] of the [SnackbarHostInteractor] and provides it to the [LocalSnackbarHostState].
 */
@Composable
fun SnackbarHostInteractor.rememberState(
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    LocalSnackbarHostState provides remember { state },
    content = content
)