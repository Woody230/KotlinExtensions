package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.container.DividedColumn
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.dialog.AlertDialogStyle
import com.bselzer.ktx.compose.ui.dialog.LocalAlertDialogStyle
import com.bselzer.ktx.compose.ui.style.*

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
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.localized(),
    painter: Painter,
    title: String,
    subtitle: String,
    buttonStyle: ButtonStyle = LocalButtonStyle.localized(),
    buttonTextStyle: WordStyle = LocalWordStyle.localized(),
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.localized(),
    dialogTitle: String = title,
    dialogTitleStyle: WordStyle = LocalWordStyle.localized(),
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

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param buttonStyle the style of the text for the dialog buttons
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleStyle the style of the text for displaying the [dialogSubtitle]
 * @param dialogSubtitleOnClick the on-click handler with the clicked character offset and the annotated [dialogSubtitle]
 * @param dialogSpacing the spacing between components within the dialog
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun TextFieldDialogPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.localized(),
    painter: Painter,
    title: String,
    subtitle: String,
    buttonStyle: ButtonStyle = LocalButtonStyle.localized(),
    buttonTextStyle: WordStyle = LocalWordStyle.localized(),
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.localized(),
    dialogTitle: String = title,
    dialogTitleStyle: WordStyle = LocalWordStyle.localized(),
    dialogSubtitle: AnnotatedString,
    dialogSubtitleStyle: WordStyle = LocalWordStyle.localized(),
    dialogSubtitleOnClick: (Int, AnnotatedString) -> Unit,
    dialogSpacing: Dp = PreferenceSpacing,
    initial: String = "",
    onStateChanged: (String?) -> Unit,
) = TextFieldDialogPreference(
    initial = initial,
    onStateChanged = onStateChanged,
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    buttonStyle = buttonStyle,
    buttonTextStyle = buttonTextStyle,
    dialogStyle = dialogStyle,
    dialogTitle = dialogTitle,
    dialogTitleStyle = dialogTitleStyle
) { editText ->
    DividedColumn(
        style = ColumnStyle(modifier = Modifier.fillMaxWidth()),
        divider = { Spacer(height = dialogSpacing) },
        contents = arrayOf(
            { ClickableText(text = dialogSubtitle, style = dialogSubtitleStyle.textStyle, onClick = { dialogSubtitleOnClick(it, dialogSubtitle) }) },
            { TextField(value = editText.value ?: "", onValueChange = { editText.value = it }) }
        )
    )
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param buttonStyle the style of the text for the dialog buttons
 * @param dialogTitle the name of the preference
 * @param dialogTitleStyle the style of the text for displaying the [dialogTitle]
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleStyle the style of the text for displaying the [dialogSubtitle]
 * @param dialogSpacing the spacing between components within the dialog
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun TextFieldDialogPreference(
    style: SimplePreferenceStyle = LocalSimplePreferenceStyle.localized(),
    painter: Painter,
    title: String,
    subtitle: String,
    buttonStyle: ButtonStyle = LocalButtonStyle.localized(),
    buttonTextStyle: WordStyle = LocalWordStyle.localized(),
    dialogStyle: AlertDialogStyle = LocalAlertDialogStyle.localized(),
    dialogTitle: String = title,
    dialogTitleStyle: WordStyle = LocalWordStyle.localized(),
    dialogSubtitle: String,
    dialogSubtitleStyle: WordStyle = LocalWordStyle.localized(),
    dialogSpacing: Dp = PreferenceSpacing,
    initial: String = "",
    onStateChanged: (String?) -> Unit,
) = TextFieldDialogPreference(
    initial = initial,
    onStateChanged = onStateChanged,
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    buttonStyle = buttonStyle,
    buttonTextStyle = buttonTextStyle,
    dialogStyle = dialogStyle,
    dialogTitle = dialogTitle,
    dialogTitleStyle = dialogTitleStyle
) { editText ->
    DividedColumn(
        style = ColumnStyle(modifier = Modifier.fillMaxWidth()),
        divider = { Spacer(height = dialogSpacing) },
        contents = arrayOf(
            { Text(text = dialogSubtitle, style = dialogSubtitleStyle) },
            { TextField(value = editText.value ?: "", onValueChange = { editText.value = it }) }
        )
    )
}