package com.bselzer.ktx.compose.ui.layout.preference.alertdialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class AlertDialogPreferenceProjector(
    interactor: AlertDialogPreferenceInteractor,
    presenter: AlertDialogPreferencePresenter = AlertDialogPreferencePresenter.Default
) : Projector<AlertDialogPreferenceInteractor, AlertDialogPreferencePresenter>(interactor, presenter) {

    /**
     * @param ending the composable function for laying out the end of the preference next to the description
     * @param dialog the composable function for layout out the body of the dialog
     */
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        ending: @Composable (() -> Unit)? = null,
        dialog: @Composable () -> Unit,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val preferenceProjector = PreferenceProjector(interactor.preference, presenter.preference)
        val dialogProjector = AlertDialogProjector(interactor.dialog, presenter.dialog)

        dialogProjector.Projection(content = dialog)
        preferenceProjector.Projection(combinedModifier, ending = ending)
    }
}