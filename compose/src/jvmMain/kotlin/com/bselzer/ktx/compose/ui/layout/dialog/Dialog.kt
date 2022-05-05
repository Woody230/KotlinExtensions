package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.DialogWindowScope
import androidx.compose.ui.window.rememberDialogState

/**
 * Opens a dialog with the given [content].
 *
 * The dialog is visible as long as it is part of the composition hierarchy.
 * In order to let the user dismiss the Dialog, the implementation of [onDismissRequest] should contain a way to remove to remove the dialog from the composition hierarchy.
 *
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param properties [DialogProperties] for further customization of this dialog's behavior
 * @param content The content to be displayed inside the dialog.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun Dialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
) = Dialog(onDismissRequest = onDismissRequest, properties = properties, onPreviewKeyEvent = { false }, content = { content() })

/**
 * Opens a dialog with the given [content].
 *
 * The dialog is visible as long as it is part of the composition hierarchy.
 * In order to let the user dismiss the Dialog, the implementation of [onDismissRequest] should contain a way to remove to remove the dialog from the composition hierarchy.
 *
 * @param onDismissRequest Executes when the user tries to dismiss the dialog.
 * @param properties [DialogProperties] for further customization of this dialog's behavior
 * @param title Title in the titlebar of the dialog
 * @param icon Icon in the titlebar of the dialog (for platforms which support this)
 * @param state The state object to be used to control or observe the dialog's state
 * When size/position is changed by the user, state will be updated.
 * When size/position of the dialog is changed by the application (changing state),
 * the native dialog will update its corresponding properties.
 * If [DialogState.position] is not [WindowPosition.isSpecified], then after the first show on the
 * screen [DialogState.position] will be set to the absolute values.
 * @param onPreviewKeyEvent This callback is invoked when the user interacts with the hardware
 * keyboard. It gives ancestors of a focused component the chance to intercept a [KeyEvent].
 * Return true to stop propagation of this event. If you return false, the key event will be
 * sent to this [onPreviewKeyEvent]'s child. If none of the children consume the event,
 * it will be sent back up to the root using the onKeyEvent callback.
 * @param onKeyEvent This callback is invoked when the user interacts with the hardware
 * keyboard. While implementing this callback, return true to stop propagation of this event.
 * If you return false, the key event will be sent to this [onKeyEvent]'s parent.
 * @param content The content to be displayed inside the dialog.
 */
@Composable
private fun Dialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    title: String = "Untitled",
    icon: Painter? = null,
    state: DialogState = rememberDialogState(),
    onPreviewKeyEvent: ((KeyEvent) -> Boolean) = { false },
    onKeyEvent: ((KeyEvent) -> Boolean) = { false },
    content: @Composable DialogWindowScope.() -> Unit
) = androidx.compose.ui.window.Dialog(
    // TODO will need to manage these properties since it provides a title within the content and not the window, control visibility
    onCloseRequest = onDismissRequest,
    state = state,
    visible = properties.visible,
    title = title,
    icon = icon,
    undecorated = properties.undecorated,
    transparent = properties.transparent,
    resizable = properties.resizable,
    enabled = properties.enabled,
    focusable = properties.focusable,
    onPreviewKeyEvent = onPreviewKeyEvent,
    onKeyEvent = onKeyEvent,
    content = content,
)