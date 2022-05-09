package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.icon.DownIcon
import com.bselzer.ktx.compose.ui.layout.icon.UpIcon
import com.bselzer.ktx.compose.ui.layout.spacer.Spacer
import com.bselzer.ktx.compose.ui.picker.NumberPicker
import com.bselzer.ktx.compose.ui.picker.ValuePicker
import com.bselzer.ktx.resource.strings.resource
import dev.icerock.moko.resources.compose.stringResource
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * Lays out a dialog for selecting an amount of [DurationUnit] representing a [Duration].
 *
 * @param onStateChanged the block for setting the updated state
 * @param style the style describing how to lay out the preference
 * @param painter the icon representing the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param dialogTitle the name of the preference
 * @param initialAmount the initial amount of the [initialUnit]
 * @param initialUnit the type of [Duration] component of [initialAmount]
 * @param minimum the minimum duration
 * @param maximum the maximum duration
 * @param units the selectable [DurationUnit] types
 */
@Composable
fun DurationDialogPreference(
    onStateChanged: (Duration?) -> Unit,
    style: DialogPreferenceStyle = LocalDialogPreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    dialogTitle: String = title,
    initialAmount: Int,
    initialUnit: DurationUnit,
    minimum: Duration = 0.days,
    maximum: Duration = Int.MAX_VALUE.days,
    units: List<DurationUnit> = DurationUnit.values().toList(),
    upIcon: @Composable () -> Unit = { UpIcon() },
    downIcon: @Composable () -> Unit = { DownIcon() },
) {
    val state = remember { mutableStateOf<Duration?>(null) }
    DialogPreference(
        style = style,
        painter = painter,
        title = title,
        subtitle = subtitle,
        dialogTitle = dialogTitle,
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
            Spacer(width = style.dialogSpacing)
            ValuePicker(value = unit.value, values = units, labels = units.map { component -> stringResource(component.resource()) }, upIcon = upIcon, downIcon = downIcon) {
                unit.value = it
            }
        }
    }
}