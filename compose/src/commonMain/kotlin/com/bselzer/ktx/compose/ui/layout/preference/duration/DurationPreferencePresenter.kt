package com.bselzer.ktx.compose.ui.layout.preference.duration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.picker.PickerPresenter
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class DurationPreferencePresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
    val preference: AlertDialogPreferencePresenter = AlertDialogPreferencePresenter.Default,
    val picker: PickerPresenter = PickerPresenter.Default
) : Presenter<DurationPreferencePresenter>(modifiers) {
    companion object {
        @Stable
        val Default = DurationPreferencePresenter()
    }

    override fun safeMerge(other: DurationPreferencePresenter) = DurationPreferencePresenter(
        modifiers = modifiers.merge(other.modifiers),
        preference = preference.merge(other.preference),
        picker = picker.merge(other.picker)
    )

    @Composable
    override fun localized() = copy(
        preference = preference.localized(),
        picker = picker.localized()
    )
}