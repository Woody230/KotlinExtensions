package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

// TODO remove when AlertDialog is able to accessed in common https://github.com/JetBrains/compose-multiplatform/issues/762
@Composable
internal expect fun AlertDialog(
    onDismissRequest: () -> Unit,
    buttons: @Composable () -> Unit,
    modifier: Modifier,
    title: (@Composable () -> Unit)?,
    text: @Composable (() -> Unit)?,
    shape: Shape,
    backgroundColor: Color,
    contentColor: Color,
)