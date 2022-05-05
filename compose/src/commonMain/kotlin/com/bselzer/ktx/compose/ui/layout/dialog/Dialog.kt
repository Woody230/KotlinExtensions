package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.runtime.Composable

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
@Composable
expect fun Dialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties, // TODO = DialogProperties(), -- can't use defaults https://github.com/JetBrains/compose-jb/issues/1407#issuecomment-997854105
    content: @Composable () -> Unit
)