package com.bselzer.ktx.compose.ui.layout.preference.alertdialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogPresenter
import com.bselzer.ktx.compose.ui.layout.description.descriptionTitlePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.PreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class AlertDialogPreferencePresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
    val preference: PreferencePresenter = PreferencePresenter.Default,
    val dialog: AlertDialogPresenter = AlertDialogPresenter.Default,
) : Presenter<AlertDialogPreferencePresenter>(modifiers) {
    companion object {
        @Stable
        val Default = AlertDialogPreferencePresenter()
    }

    override fun safeMerge(other: AlertDialogPreferencePresenter) = AlertDialogPreferencePresenter(
        modifiers = modifiers.merge(other.modifiers),
        preference = preference.merge(other.preference),
        dialog = dialog.merge(other.dialog)
    )

    @Composable
    override fun localized() = AlertDialogPreferencePresenter(
        dialog = AlertDialogPresenter(
            title = descriptionTitlePresenter()
        )
    ).merge(this).run {
        copy(
            preference = preference.localized(),
            dialog = dialog.localized()
        )
    }
}