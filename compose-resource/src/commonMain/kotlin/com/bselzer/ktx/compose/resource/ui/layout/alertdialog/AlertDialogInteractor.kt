package com.bselzer.ktx.compose.resource.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.resource.ui.layout.text.confirmationTextInteractor
import com.bselzer.ktx.compose.resource.ui.layout.text.dismissTextInteractor
import com.bselzer.ktx.compose.resource.ui.layout.text.resetTextInteractor
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogInteractor

/**
 * Creates a new [AlertDialogInteractor.Builder] with [triText] enabled.
 */
@Composable
fun triTextAlertDialogInteractor(closeDialog: () -> Unit) = AlertDialogInteractor.Builder(
    closeDialog = closeDialog
).triText()

/**
 * Creates a new [AlertDialogInteractor.Builder] with [biText] enabled.
 */
@Composable
fun biTextAlertDialogInteractor(closeDialog: () -> Unit) = AlertDialogInteractor.Builder(
    closeDialog = closeDialog
).biText()

/**
 * Creates a new [AlertDialogInteractor.Builder] with [uniText] enabled.
 */
@Composable
fun uniTextAlertDialogInteractor(closeDialog: () -> Unit) = AlertDialogInteractor.Builder(
    closeDialog = closeDialog
).uniText()

/**
 * Enables the negative button with dismissal text.
 *
 * Enables the neutral button with reset text.
 *
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.triText() = apply {
    neutralText = resetTextInteractor()
    biText()
}

/**
 * Enables the negative button with dismissal text.
 *
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.biText() = apply {
    negativeText = dismissTextInteractor()
    uniText()
}

/**
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.uniText() = apply {
    positiveText = confirmationTextInteractor()
}