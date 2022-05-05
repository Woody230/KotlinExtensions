package com.bselzer.ktx.compose.ui.layout.preference

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
import com.bselzer.ktx.compose.ui.container.DividedColumn
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.style.ColumnStyle


/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 * @param style the style describing how to lay out the preference
 * @param painter the icon representing the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param dialogTitle the name of the preference
 * @param dialogContent the block for displaying the main dialog content
 */
@Composable
fun TextFieldDialogPreference(
    initial: String = "",
    onStateChanged: (String?) -> Unit,
    style: DialogPreferenceStyle = LocalDialogPreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    dialogTitle: String = title,
    dialogContent: @Composable (MutableState<String?>) -> Unit,
) {
    val editText = remember { mutableStateOf<String?>(initial) }
    DialogPreference(
        style = style,
        painter = painter,
        title = title,
        subtitle = subtitle,
        dialogTitle = dialogTitle,
        state = editText,
        onStateChanged = onStateChanged,
    ) {
        dialogContent(editText)
    }
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the icon representing the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param dialogTitle the name of the preference
 * @param dialogSubtitle the description of the preference for input
 * @param dialogSubtitleOnClick the on-click handler with the clicked character offset and the annotated [dialogSubtitle]
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun TextFieldDialogPreference(
    style: DialogPreferenceStyle = LocalDialogPreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    dialogTitle: String = title,
    dialogSubtitle: AnnotatedString,
    dialogSubtitleOnClick: (Int, AnnotatedString) -> Unit,
    initial: String = "",
    onStateChanged: (String?) -> Unit,
) = TextFieldDialogPreference(
    initial = initial,
    onStateChanged = onStateChanged,
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    dialogTitle = dialogTitle,
) { editText ->
    DividedColumn(
        style = ColumnStyle(modifier = Modifier.fillMaxWidth()),
        divider = { Spacer(height = style.dialogSpacing) },
        contents = arrayOf(
            { ClickableText(text = dialogSubtitle, style = style.dialogTextStyle.textStyle, onClick = { dialogSubtitleOnClick(it, dialogSubtitle) }) },
            { TextField(value = editText.value ?: "", onValueChange = { editText.value = it }) }
        )
    )
}

/**
 * Lays out a dialog with an editable [Text] representing a [String] preference state.
 *
 * @param style the style describing how to lay out the preference
 * @param painter the icon representing the preference
 * @param title the name of the preference
 * @param subtitle the description of the preference
 * @param dialogTitle the name of the preference
 * @param dialogSubtitle the description of the preference for input
 * @param initial the initial state of the [TextField]
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun TextFieldDialogPreference(
    style: DialogPreferenceStyle = LocalDialogPreferenceStyle.current,
    painter: Painter,
    title: String,
    subtitle: String,
    dialogTitle: String = title,
    dialogSubtitle: String,
    initial: String = "",
    onStateChanged: (String?) -> Unit,
) = TextFieldDialogPreference(
    initial = initial,
    onStateChanged = onStateChanged,
    style = style,
    painter = painter,
    title = title,
    subtitle = subtitle,
    dialogTitle = dialogTitle,
) { editText ->
    DividedColumn(
        style = ColumnStyle(modifier = Modifier.fillMaxWidth()),
        divider = { Spacer(height = style.dialogSpacing) },
        contents = arrayOf(
            { Text(text = dialogSubtitle, style = style.dialogTextStyle) },
            { TextField(value = editText.value ?: "", onValueChange = { editText.value = it }) }
        )
    )
}