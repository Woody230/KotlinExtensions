package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

internal actual class AlertDialogProjectionImpl actual constructor(
    logic: AlertDialogLogic,
    presentation: AlertDialogPresentation
) : AlertDialogProjection(logic, presentation) {
    @Composable
    override fun project(
        onDismissRequest: () -> Unit,
        buttons: @Composable () -> Unit,
        modifier: Modifier,
        title: @Composable (() -> Unit)?,
        text: @Composable (() -> Unit)?,
        shape: Shape,
        backgroundColor: Color,
        contentColor: Color
    ) = AlertDialog(
        onDismissRequest = onDismissRequest,
        buttons = buttons,
        modifier = modifier,
        title = title,
        text = text,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor
    )
}