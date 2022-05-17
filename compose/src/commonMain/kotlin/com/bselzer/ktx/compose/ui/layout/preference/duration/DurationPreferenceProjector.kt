package com.bselzer.ktx.compose.ui.layout.preference.duration

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.picker.IntegerPickerInteractor
import com.bselzer.ktx.compose.ui.layout.picker.PickerProjector
import com.bselzer.ktx.compose.ui.layout.picker.ValuePickerInteractor
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceConstants
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferenceProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.toDuration

class DurationPreferenceProjector(
    interactor: DurationPreferenceInteractor,
    presenter: DurationPreferencePresenter = DurationPreferencePresenter.Default
) : Projector<DurationPreferenceInteractor, DurationPreferencePresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        showDialog: Boolean
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val preferenceProjector = AlertDialogPreferenceProjector(interactor.preference, presenter.preference)

        preferenceProjector.Projection(
            modifier = combinedModifier,
            showDialog = showDialog,
            ending = null,
        ) {
            DialogContent(interactor, presenter)
        }
    }

    @Composable
    private fun DialogContent(interactor: DurationPreferenceInteractor, presenter: DurationPreferencePresenter) {
        val state = remember { mutableStateOf<Duration?>(null) }

        // Converted minimum can produce 0 because of being rounded down so set the minimum bound to at least 1. (ex: 30 seconds => 0 minutes)
        val unit = remember { mutableStateOf(interactor.initialUnit) }
        val convertedMin = max(1, interactor.minimum.toInt(unit.value))
        val convertedMax = interactor.maximum.toInt(unit.value)
        fun bounded(value: Int) = min(convertedMax, max(convertedMin, value))

        // Adjust the current value as the component changes to make sure it is bounded.
        val amount = remember { mutableStateOf(interactor.initialAmount) }
        amount.value = bounded(amount.value)

        // Update the state for the current amount and unit.
        state.value = amount.value.toDuration(unit.value)

        @Composable
        fun NumberPicker() = PickerProjector(
            presenter = presenter.picker,
            interactor = IntegerPickerInteractor(
                selected = amount.value,
                range = convertedMin..convertedMax,
                upIcon = interactor.upIcon,
                downIcon = interactor.downIcon,
                onSelectionChanged = { amount.value = bounded(it) }
            ),
        ).Projection()

        @Composable
        fun UnitPicker() = PickerProjector(
            presenter = presenter.picker,
            interactor = ValuePickerInteractor(
                selected = unit.value,
                values = interactor.units,
                getLabel = interactor.getLabel,
                upIcon = interactor.upIcon,
                downIcon = interactor.downIcon,
                onSelectionChanged = { unit.value = it }
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