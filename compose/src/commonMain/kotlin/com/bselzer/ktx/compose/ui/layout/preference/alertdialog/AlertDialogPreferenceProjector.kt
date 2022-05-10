package com.bselzer.ktx.compose.ui.layout.preference.alertdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class AlertDialogPreferenceProjector(
    override val interactor: AlertDialogPreferenceInteractor,
    override val presenter: AlertDialogPreferencePresenter = AlertDialogPreferencePresenter.Default
) : Projector<AlertDialogPreferenceInteractor, AlertDialogPreferencePresenter>() {
    private val preferenceProjector = PreferenceProjector(interactor.preference, presenter.preference)
    private val dialogProjector = AlertDialogProjector(interactor.dialog, presenter.dialog)

    /**
     * @param showDialog whether the dialog should be shown
     * @param ending the composable function for laying out the end of the preference next to the description
     * @param dialog the composable function for layout out the body of the dialog
     */
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        showDialog: Boolean,
        ending: @Composable (() -> Unit)? = null,
        dialog: @Composable () -> Unit,
    ) = contextualize(modifier) { combinedModifier ->
        if (showDialog) {
            dialogProjector.project(content = dialog)
        }

        preferenceProjector.project(combinedModifier, ending = ending)
    }
}