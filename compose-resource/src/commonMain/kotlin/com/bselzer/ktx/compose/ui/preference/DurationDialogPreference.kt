package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.dialog.AlertDialogStyle
import com.bselzer.ktx.compose.ui.dialog.LocalAlertDialogStyle
import com.bselzer.ktx.compose.ui.picker.NumberPicker
import com.bselzer.ktx.compose.ui.picker.ValuePicker
import com.bselzer.ktx.compose.ui.style.ButtonStyle
import com.bselzer.ktx.compose.ui.style.LocalButtonStyle
import com.bselzer.ktx.compose.ui.style.LocalWordStyle
import com.bselzer.ktx.compose.ui.style.WordStyle
import com.bselzer.ktx.function.objects.userFriendly
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

/**
 * Lays out a dialog for selecting an amount of [DurationUnit] representing a [Duration].
 *
 * @param onStateChanged the block for setting the updated state
 * @param style the style describing how to lay out the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param buttonStyle the style of the text for the dialog buttons
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param initialAmount the initial amount of the [initialUnit]
 * @param initialUnit the type of [Duration] component of [initialAmount]
 * @param minimum the minimum duration
 * @param maximum the maximum duration
 * @param units the selectable [DurationUnit] types
 */
@OptIn(ExperimentalTime::class)
@Composable
fun DurationDialogPreference(
    onStateChanged: (Duration?) -> Unit,
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    buttonStyle: ButtonStyle = LocalButtonStyle.current,
    buttonTextStyle: WordStyle = LocalWordStyle.current,
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.current,
    dialogTitle: String = title,
    dialogTitleStyle: WordStyle = LocalWordStyle.current,
    dialogSpacing: Dp = PreferenceSpacing,
    initialAmount: Int,
    initialUnit: DurationUnit,
    minimum: Duration = 0.days,
    maximum: Duration = Int.MAX_VALUE.days,
    units: List<DurationUnit> = DurationUnit.values().toList(),
    upIcon: @Composable () -> Unit,
    downIcon: @Composable () -> Unit,
) {
    val state = remember { mutableStateOf<Duration?>(null) }
    DialogPreference(
        style = style,
        painter = painter,
        title = title,
        subtitle = subtitle,
        buttonStyle = buttonStyle,
        buttonTextStyle = buttonTextStyle,
        dialogStyle = dialogStyle,
        dialogTitle = dialogTitle,
        dialogTitleStyle = dialogTitleStyle,
        state = state,
        onStateChanged = onStateChanged,
    ) {
        // Converted minimum can produce 0 because of being rounded down so set the minimum bound to at least 1. (ex: 30 seconds => 0 minutes)
        val unit = remember { mutableStateOf(initialUnit) }
        val convertedMin = max(1, minimum.toInt(unit.value))
        val convertedMax = maximum.toInt(unit.value)
        fun bounded(value: Int) = min(convertedMax, max(convertedMin, value))

        // Adjust the current value as the component changes to make sure it is bounded.
        val amount = remember { mutableStateOf(initialAmount) }
        amount.value = bounded(amount.value)

        // Update the state for the current amount and unit.
        state.value = amount.value.toDuration(unit.value)

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberPicker(value = amount.value, range = convertedMin..convertedMax, upIcon = upIcon, downIcon = downIcon) {
                amount.value = bounded(it)
            }
            Spacer(width = dialogSpacing)
            ValuePicker(value = unit.value, values = units, labels = units.map { component -> component.userFriendly() }, upIcon = upIcon, downIcon = downIcon) {
                unit.value = it
            }
        }
    }
}