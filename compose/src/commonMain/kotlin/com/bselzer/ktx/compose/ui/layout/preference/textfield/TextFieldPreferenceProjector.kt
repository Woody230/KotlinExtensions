package com.bselzer.ktx.compose.ui.layout.preference.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.column.dividedColumnProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceConstants
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.compose.ui.layout.textfield.TextFieldProjector

class TextFieldPreferenceProjector(
    interactor: TextFieldPreferenceInteractor,
    presenter: TextFieldPreferencePresenter = TextFieldPreferencePresenter.Default
) : Projector<TextFieldPreferenceInteractor, TextFieldPreferencePresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        showDialog: Boolean,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val preferenceProjector = AlertDialogPreferenceProjector(interactor.preference, presenter.preference)
        val inputDescriptionProjector = TextProjector(interactor.inputDescription, presenter.inputDescription)
        val inputProjector = TextFieldProjector(interactor.input, presenter.input)

        preferenceProjector.Projection(
            modifier = combinedModifier,
            showDialog = showDialog,
            ending = null,
        ) {
            dividedColumnProjector(
                thickness = PreferenceConstants.Thickness
            ).Projection(
                modifier = Modifier.fillMaxWidth(),
                { inputDescriptionProjector.Projection() },
                { inputProjector.Projection() }
            )
        }
    }
}