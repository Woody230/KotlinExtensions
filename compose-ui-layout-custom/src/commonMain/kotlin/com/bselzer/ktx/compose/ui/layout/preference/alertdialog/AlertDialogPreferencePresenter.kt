package com.bselzer.ktx.compose.ui.layout.preference.alertdialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.alertdialog.AlertDialogPresenter
import com.bselzer.ktx.compose.ui.layout.description.descriptionTitlePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.preference.PreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class AlertDialogPreferencePresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val preference: PreferencePresenter = PreferencePresenter.Default,
    val dialog: AlertDialogPresenter = AlertDialogPresenter.Default,
) : Presenter<AlertDialogPreferencePresenter>(modifier) {
    companion object {
        @Stable
        val Default = AlertDialogPreferencePresenter()
    }

    override fun safeMerge(other: AlertDialogPreferencePresenter) = AlertDialogPreferencePresenter(
        modifier = modifier.merge(other.modifier),
        preference = preference.merge(other.preference),
        dialog = dialog.merge(other.dialog)
    )

    @Composable
    override fun localized() = AlertDialogPreferencePresenter(
        dialog = AlertDialogPresenter(
            title = descriptionTitlePresenter()
        )
    ).merge(this)
}