package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.runtime.Composable

@Composable
expect fun Dialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
)