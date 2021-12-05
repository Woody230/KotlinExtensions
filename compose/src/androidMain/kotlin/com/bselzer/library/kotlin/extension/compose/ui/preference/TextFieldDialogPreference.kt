package com.bselzer.library.kotlin.extension.compose.ui.preference

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.bselzer.library.kotlin.extension.compose.ui.container.DividedColumn
import com.bselzer.library.kotlin.extension.compose.ui.dialog.ConfirmationButton
import com.bselzer.library.kotlin.extension.compose.ui.dialog.DeleteButton
import com.bselzer.library.kotlin.extension.compose.ui.dialog.DismissButton
import com.bselzer.library.kotlin.extension.compose.ui.dialog.MaterialAlertDialog

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param state the preference state
 * @param onStateChanged the block for setting the updated state
 * @param spacing the spacing between components
 * @param iconPainter the painter for displaying the icon image
 * @param iconSize the size of the icon image
 * @param iconScale how to scale the icon image content
 * @param title the name of the preference
 * @param titleStyle the style of the text for displaying the [title]
 * @param subtitle the description of the preference
 * @param subtitleStyle the style of the text for displaying the [subtitle]
 * @param dialog the block for displaying the dialog with blocks for setting whether to show the dialog and for passing the new state
 */
@Composable
fun TextFieldDialogPreference(
    modifier: Modifier = Modifier,
    state: MutableState<String?>,
    onStateChanged: (String?) -> Unit = { state.value = it },
    spacing: Dp = 25.dp,
    iconPainter: Painter,
    iconSize: DpSize = DpSize(48.dp, 48.dp),
    iconScale: ContentScale = ContentScale.FillBounds,
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    subtitle: String,
    subtitleStyle: TextStyle = MaterialTheme.typography.subtitle2,
    dialog: @Composable ((Boolean) -> Unit, (String?) -> Unit) -> Unit
) = ConstraintLayout(
    modifier = Modifier
        .fillMaxWidth()
        .then(modifier)
) {
    val (icon, description) = createRefs()
    PreferenceIcon(
        ref = icon,
        contentDescription = title,
        painter = iconPainter,
        contentScale = iconScale,
        contentSize = iconSize,
    )

    var showDialog by remember { mutableStateOf(false) }
    PreferenceDescription(
        ref = description,
        startRef = icon,
        spacing = spacing,
        title = title,
        titleStyle = titleStyle,
        subtitle = subtitle,
        subtitleStyle = subtitleStyle
    )

    if (showDialog) {
        dialog({ showDialog = it }, { onStateChanged(it) })
    }
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param state the preference state
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
 * @param dialogContent the block for displaying the main dialog content
 */
@Composable
fun TextFieldDialogPreference(
    modifier: Modifier = Modifier,
    state: MutableState<String?>,
    onStateChanged: (String?) -> Unit = { state.value = it },
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
    dialogContent: @Composable (MutableState<String>) -> Unit,
) = TextFieldDialogPreference(
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
) { setShowDialog, setState ->
    val editText = remember { mutableStateOf("") }
    MaterialAlertDialog(
        showDialog = setShowDialog,
        shape = dialogShape,
        backgroundColor = dialogBackgroundColor,
        contentColor = dialogContentColor,
        properties = dialogProperties,
        title = { PreferenceTitle(title = dialogTitle, style = dialogTitleStyle) },
        negativeButton = {
            DismissButton(textStyle = buttonStyle, colors = buttonColors) { setShowDialog(false) }
        },
        neutralButton = {
            DeleteButton(textStyle = buttonStyle, colors = buttonColors) { setState(null) }
        },
        positiveButton = {
            ConfirmationButton(textStyle = buttonStyle, colors = buttonColors) { setState(editText.value) }
        },
    ) {
        dialogContent(editText)
    }
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param state the preference state
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
 * @param dialogShape the dialog shape
 * @param dialogBackgroundColor the color of the dialog background
 * @param dialogContentColor the color of the dialog content
 * @param dialogProperties the dialog properties
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleStyle the style of the text for displaying the [dialogSubtitle]
 * @param dialogSubtitleOnClick the on-click handler for the annotated [dialogSubtitle]
 */
@Composable
fun TextFieldDialogPreference(
    modifier: Modifier = Modifier,
    state: MutableState<String?>,
    onStateChanged: (String?) -> Unit = { state.value = it },
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
    dialogBackgroundColor: Color = MaterialTheme.colors.surface,
    dialogContentColor: Color = contentColorFor(dialogBackgroundColor),
    dialogProperties: DialogProperties = DialogProperties(),
    dialogTitle: String = title,
    dialogTitleStyle: TextStyle = titleStyle,
    dialogSubtitle: AnnotatedString,
    dialogSubtitleStyle: TextStyle = subtitleStyle,
    dialogSpacing: Dp = spacing,
    dialogSubtitleOnClick: (Int) -> Unit
) = TextFieldDialogPreference(
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
            { ClickableText(text = dialogSubtitle, style = dialogSubtitleStyle, onClick = dialogSubtitleOnClick) },
            { TextField(value = editText.value, onValueChange = { editText.value = it }) }
        )
    )
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param modifier the [ConstraintLayout] modifier
 * @param state the preference state
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
 * @param dialogShape the dialog shape
 * @param dialogBackgroundColor the color of the dialog background
 * @param dialogContentColor the color of the dialog content
 * @param dialogProperties the dialog properties
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleStyle the style of the text for displaying the [dialogSubtitle]
 */
@Composable
fun TextFieldDialogPreference(
    modifier: Modifier = Modifier,
    state: MutableState<String?>,
    onStateChanged: (String?) -> Unit = { state.value = it },
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
    dialogBackgroundColor: Color = MaterialTheme.colors.surface,
    dialogContentColor: Color = contentColorFor(dialogBackgroundColor),
    dialogProperties: DialogProperties = DialogProperties(),
    dialogTitle: String = title,
    dialogTitleStyle: TextStyle = titleStyle,
    dialogSubtitle: String,
    dialogSubtitleStyle: TextStyle = subtitleStyle,
    dialogSpacing: Dp = spacing
) = TextFieldDialogPreference(
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
            { TextField(value = editText.value, onValueChange = { editText.value = it }) }
        )
    )
}