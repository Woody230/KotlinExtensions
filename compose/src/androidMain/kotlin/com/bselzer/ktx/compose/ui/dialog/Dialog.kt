package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.bselzer.ktx.compose.ui.style.DialogProperties

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
) = androidx.compose.ui.window.Dialog(
    onDismissRequest = onDismissRequest,
    properties = androidx.compose.ui.window.DialogProperties(
        dismissOnBackPress = properties.dismissOnBackPress,
        dismissOnClickOutside = properties.dismissOnClickOutside,
        securePolicy = properties.securePolicy,
        usePlatformDefaultWidth = properties.usePlatformDefaultWidth
    ),
    content = content
)