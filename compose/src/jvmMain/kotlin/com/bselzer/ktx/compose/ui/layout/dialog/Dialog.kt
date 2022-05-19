package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.runtime.Composable

@Composable
actual fun Dialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
) = androidx.compose.ui.window.Dialog(
    onCloseRequest = onDismissRequest,
    content = { content() }
)