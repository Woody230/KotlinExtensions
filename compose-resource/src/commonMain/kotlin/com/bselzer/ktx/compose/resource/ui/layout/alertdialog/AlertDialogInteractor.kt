package com.bselzer.ktx.compose.resource.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.resource.strings.localized
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogInteractor
import com.bselzer.ktx.resource.Resources

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
    neutralText = Resources.strings.reset.localized()
    biText()
}

/**
 * Enables the negative button with dismissal text.
 *
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.biText() = apply {
    negativeText = Resources.strings.cancel.localized()
    uniText()
}

/**
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.uniText() = apply {
    positiveText = Resources.strings.ok.localized()
}