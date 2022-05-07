package com.bselzer.ktx.compose.ui.layout.dialog

internal expect class AlertDialogProjectionImpl(
    logic: AlertDialogLogic,
    presentation: AlertDialogPresentation
) : AlertDialogProjection