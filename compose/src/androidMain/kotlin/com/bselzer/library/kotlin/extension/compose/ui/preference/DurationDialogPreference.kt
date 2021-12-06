package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.bselzer.library.kotlin.extension.compose.ui.picker.NumberPicker
import com.bselzer.library.kotlin.extension.compose.ui.picker.ValuePicker
import com.bselzer.library.kotlin.extension.function.objects.userFriendly
import kotlin.math.max
import kotlin.math.min
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

/**
 * Lays out a dialog for selecting an amount of [DurationUnit] representing a [Duration].
 *
 * @param modifier the dialog modifier
 * @param onStateChanged the block for setting the updated state
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonColors the colors of the dialog buttons
 * @param dialogShape the dialog shape
 * @param dialogBackgroundColor the color of the dialog background
 * @param dialogContentColor the color of the dialog content
 * @param dialogProperties the dialog properties
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
    modifier: Modifier = Modifier,
    onStateChanged: (Duration?) -> Unit,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    dialogShape: Shape = MaterialTheme.shapes.medium,
    dialogBackgroundColor: Color = MaterialTheme.colors.surface,
    dialogContentColor: Color = contentColorFor(dialogBackgroundColor),
    dialogProperties: DialogProperties = DialogProperties(),
    dialogTitle: String = title,
    dialogTitleStyle: TextStyle = titleStyle,
    initialAmount: Int,
    initialUnit: DurationUnit,
    minimum: Duration = Duration.days(0),
    maximum: Duration = Duration.days(Int.MAX_VALUE),
    units: List<DurationUnit> = DurationUnit.values().toList()
) {
    val state = remember { mutableStateOf<Duration?>(null) }
    DialogPreference(
        modifier = modifier,
        state = state,
        onStateChanged = onStateChanged,
        spacing = spacing,
        iconPainter = iconPainter,
        iconSize = iconSize,
        iconScale = iconScale,
        title = title,
        titleStyle = titleStyle,
        subtitle = subtitle,
        subtitleStyle = subtitleStyle,
        buttonStyle = buttonStyle,
        buttonColors = buttonColors,
        dialogShape = dialogShape,
        dialogBackgroundColor = dialogBackgroundColor,
        dialogContentColor = dialogContentColor,
        dialogProperties = dialogProperties,
        dialogTitle = dialogTitle,
        dialogTitleStyle = dialogTitleStyle
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
            NumberPicker(value = amount.value, range = convertedMin..convertedMax) {
                amount.value = bounded(it)
            }
            Spacer(modifier = Modifier.width(spacing))
            ValuePicker(value = unit.value, values = units, labels = units.map { component -> component.userFriendly() }) {
                unit.value = it
            }
        }
    }
}