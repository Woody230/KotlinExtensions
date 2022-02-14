package com.bselzer.ktx.compose.ui.preference

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.dialog.AlertDialogStyle
import com.bselzer.ktx.compose.ui.dialog.LocalAlertDialogStyle
import com.bselzer.ktx.compose.ui.style.ButtonStyle
import com.bselzer.ktx.compose.ui.style.LocalButtonStyle
import com.bselzer.ktx.compose.ui.style.LocalWordStyle
import com.bselzer.ktx.compose.ui.style.WordStyle

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param initial the initial state of the [TextField]
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param buttonStyle the style of the text for the dialog buttons
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogContent the block for displaying the main dialog content
 */
@Composable
fun TextFieldDialogPreference(
    initial: String = "",
    onStateChanged: (String?) -> Unit,
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    buttonStyle: ButtonStyle = LocalButtonStyle.current,
    buttonTextStyle: WordStyle = LocalWordStyle.current,
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.current,
    dialogTitle: String = title,
    dialogTitleStyle: WordStyle = LocalWordStyle.current,
    dialogContent: @Composable (MutableState<String?>) -> Unit,
) {
    val editText = remember { mutableStateOf<String?>(initial) }
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
        state = editText,
        onStateChanged = onStateChanged,
    ) {
        dialogContent(editText)
    }
}

/* TODO text field
/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param modifier the dialog modifier
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
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleStyle the style of the text for displaying the [dialogSubtitle]
 * @param dialogSubtitleOnClick the on-click handler with the clicked character offset and the annotated [dialogSubtitle]
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun TextFieldDialogPreference(
    modifier: Modifier = Modifier,
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    dialogShape: Shape = MaterialTheme.shapes.medium,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    dialogBackgroundColor: Color = MaterialTheme.colors.surface,
    dialogContentColor: Color = contentColorFor(dialogBackgroundColor),
    dialogProperties: DialogProperties = DialogProperties(),
    dialogTitle: String = title,
    dialogTitleStyle: TextStyle = titleStyle,
    dialogSubtitle: AnnotatedString,
    dialogSubtitleStyle: TextStyle = subtitleStyle,
    dialogSpacing: Dp = spacing,
    dialogSubtitleOnClick: (Int, AnnotatedString) -> Unit,
    initial: String = "",
    onStateChanged: (String?) -> Unit,
) = TextFieldDialogPreference(
    modifier = modifier,
    initial = initial,
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
) { editText ->
    DividedColumn(
        modifier = Modifier.fillMaxWidth(),
        divider = { Spacer(modifier = Modifier.height(dialogSpacing)) },
        contents = arrayOf(
            { ClickableText(text = dialogSubtitle, style = dialogSubtitleStyle, onClick = { dialogSubtitleOnClick(it, dialogSubtitle) }) },
            { TextField(value = editText.value ?: "", onValueChange = { editText.value = it }) }
        )
    )
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param modifier the dialog modifier
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
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleStyle the style of the text for displaying the [dialogSubtitle]
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun TextFieldDialogPreference(
    modifier: Modifier = Modifier,
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
    dialogSubtitle: String,
    dialogSubtitleStyle: TextStyle = subtitleStyle,
    dialogSpacing: Dp = spacing,
    initial: String = "",
    onStateChanged: (String?) -> Unit,
) = TextFieldDialogPreference(
    modifier = modifier,
    initial = initial,
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
) { editText ->
    DividedColumn(
        modifier = Modifier.fillMaxWidth(),
        divider = { Spacer(modifier = Modifier.height(dialogSpacing)) },
        contents = arrayOf(
            { Text(text = dialogSubtitle, style = dialogSubtitleStyle) },
            { TextField(value = editText.value ?: "", onValueChange = { editText.value = it }) }
        )
    )
}
*/