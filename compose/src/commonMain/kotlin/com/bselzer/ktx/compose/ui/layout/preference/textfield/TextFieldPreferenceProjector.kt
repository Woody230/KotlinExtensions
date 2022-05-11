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
    override val interactor: TextFieldPreferenceInteractor,
    override val presenter: TextFieldPreferencePresenter = TextFieldPreferencePresenter.Default
) : Projector<TextFieldPreferenceInteractor, TextFieldPreferencePresenter>() {
    private val preferenceProjector = AlertDialogPreferenceProjector(interactor.preference, presenter.preference)
    private val inputDescriptionProjector = TextProjector(interactor.inputDescription, presenter.inputDescription)
    private val inputProjector = TextFieldProjector(interactor.input, presenter.input)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        showDialog: Boolean,
    ) = contextualize(modifier) { combinedModifier ->
        preferenceProjector.Projection(
            modifier = combinedModifier,
            showDialog = showDialog,
            ending = null,
        ) {
            DialogContent()
        }
    }

    @Composable
    private fun DialogContent() = dividedColumnProjector(
        thickness = PreferenceConstants.Thickness
    ).Projection(
        modifier = Modifier.fillMaxWidth(),
        { inputDescriptionProjector.Projection() },
        { inputProjector.Projection() }
    )
}