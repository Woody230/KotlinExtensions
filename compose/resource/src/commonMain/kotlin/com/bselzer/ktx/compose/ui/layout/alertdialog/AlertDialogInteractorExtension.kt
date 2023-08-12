package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.runtime.Composable
import com.bselzer.ktx.resource.KtxResources
import com.bselzer.ktx.resource.strings.localized

/**
 * Enables the negative button with dismissal text.
 *
 * Enables the neutral button with reset text.
 *
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.triText() = apply {
    neutralText = KtxResources.strings.reset.localized()
    biText()
}

/**
 * Enables the negative button with dismissal text.
 *
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.biText() = apply {
    negativeText = KtxResources.strings.cancel.localized()
    uniText()
}

/**
 * Enables the positive button with confirmation text.
 */
@Composable
fun AlertDialogInteractor.Builder.uniText() = apply {
    positiveText = KtxResources.strings.ok.localized()
}