package com.bselzer.ktx.compose.ui.layout.preference.textfield

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter
import com.bselzer.ktx.compose.ui.layout.textfield.TextFieldPresenter

data class TextFieldPreferencePresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presenter] for core preference and dialog properties.
     */
    val preference: AlertDialogPreferencePresenter = AlertDialogPreferencePresenter.Default,

    /**
     * The [Presenter] for the subtitle of the dialog, describing the preference for input.
     */
    val inputDescription: TextPresenter = TextPresenter.Default,

    /**
     * The [Presenter] for the input field.
     */
    val input: TextFieldPresenter = TextFieldPresenter.Default,
) : Presenter<TextFieldPreferencePresenter>(modifier) {
    companion object {
        @Stable
        val Default = TextFieldPreferencePresenter()
    }

    override fun safeMerge(other: TextFieldPreferencePresenter) = TextFieldPreferencePresenter(
        modifier = modifier.merge(other.modifier),
        preference = preference.merge(other.preference),
        inputDescription = inputDescription.merge(other.inputDescription),
        input = input.merge(other.input)
    )
}