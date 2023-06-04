package com.bselzer.ktx.compose.ui.layout.preference.duration

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.picker.IntegerPickerInteractor
import com.bselzer.ktx.compose.ui.layout.picker.PickerProjector
import com.bselzer.ktx.compose.ui.layout.picker.ValuePickerInteractor
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceConstants
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DurationPreferenceProjector(
    interactor: DurationPreferenceInteractor,
    presenter: DurationPreferencePresenter = DurationPreferencePresenter.Default
) : Projector<DurationPreferenceInteractor, DurationPreferencePresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val preferenceProjector = AlertDialogPreferenceProjector(interactor.preference, presenter.preference)

        preferenceProjector.Projection(
            modifier = combinedModifier,
            ending = null,
        ) {
            DialogContent(interactor, presenter)
        }
    }

    @Composable
    private fun DialogContent(interactor: DurationPreferenceInteractor, presenter: DurationPreferencePresenter) {
        @Composable
        fun NumberPicker() = PickerProjector(
            presenter = presenter.picker,
            interactor = IntegerPickerInteractor(
                selected = interactor.amount,
                range = interactor.amountRange,
                upIcon = interactor.upIcon,
                downIcon = interactor.downIcon,
                onSelectionChanged = { interactor.onValueChange(it, interactor.unit) }
            ),
        ).Projection()

        @Composable
        fun UnitPicker() = PickerProjector(
            presenter = presenter.picker,
            interactor = ValuePickerInteractor(
                selected = interactor.unit,
                values = interactor.units,
                getLabel = interactor.unitLabel,
                upIcon = interactor.upIcon,
                downIcon = interactor.downIcon,
                onSelectionChanged = { interactor.onValueChange(interactor.amount, it) }
            )
        ).Projection()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            NumberPicker()
            Spacer(modifier = Modifier.width(PreferenceConstants.Spacing))
            UnitPicker()
        }
    }
}