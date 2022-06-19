package com.bselzer.ktx.compose.ui.layout.preference.duration

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.picker.PickerPresenter
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class DurationPreferencePresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val preference: AlertDialogPreferencePresenter = AlertDialogPreferencePresenter.Default,
    val picker: PickerPresenter = PickerPresenter.Default
) : Presenter<DurationPreferencePresenter>(modifier) {
    companion object {
        @Stable
        val Default = DurationPreferencePresenter()
    }

    override fun safeMerge(other: DurationPreferencePresenter) = DurationPreferencePresenter(
        modifier = modifier.merge(other.modifier),
        preference = preference.merge(other.preference),
        picker = picker.merge(other.picker)
    )
}