package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.appbar.MaterialAppBarTitle
import com.bselzer.ktx.compose.ui.style.*

/**
 * Lays out a dialog for choosing a single item out of multiple items.
 *
 * @param setShowDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param title the title describing the choices
 * @param titleStyle the style of the text for displaying the [title]
 * @param buttonStyle the style of the dialog buttons
 * @param buttonTextStyle the style of the text for the dialog buttons
 * @param selected the state managing the currently selected item
 * @param onStateChanged the block for setting the updated state
 * @param choices the block for laying out the item choices
 */
@Composable
fun <T> SingleChoiceDialog(
    setShowDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { setShowDialog(false) },
    style: AlertDialogStyle = LocalAlertDialogStyle.current,
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current,
    buttonStyle: ButtonStyle = LocalButtonStyle.current,
    buttonTextStyle: WordStyle = LocalWordStyle.current,
    selected: T?,
    onStateChanged: (T) -> Unit,
    choices: @Composable () -> Unit
) = MaterialAlertDialog(
    style = style,
    onDismissRequest = onDismissRequest,
    content = choices,
    title = {
        val textStyle = WordStyle(textStyle = MaterialTheme.typography.h6).with(titleStyle)
        Column(modifier = Modifier.fillMaxWidth()) {
            MaterialAppBarTitle(title = title, style = textStyle)
            Divider(thickness = 1.dp)
        }
    },
    buttons = {
        val textStyle = WordStyle(textStyle = MaterialTheme.typography.button).with(buttonTextStyle)
        Column(modifier = Modifier.fillMaxWidth()) {
            Divider(thickness = 1.dp)
            MaterialAlertDialogButtons(
                negativeButton = {
                    DismissButton(style = buttonStyle, textStyle = textStyle) { setShowDialog(false) }
                },
                positiveButton = {
                    ConfirmationButton(style = buttonStyle.copy(enabled = selected != null), textStyle = textStyle) {
                        selected?.let { selected ->
                            onStateChanged(selected)
                            setShowDialog(false)
                        }
                    }
                },
            )
        }
    }
)

/**
 * Lays out a dialog for choosing a single item out of multiple items.
 *
 * @param showDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param title the title describing the choices
 * @param titleStyle the style of the text for displaying the [title]
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonTextStyle the style of the text for the dialog buttons
 * @param radioButtonStyle the style of the radio button for selecting a choice
 * @param labelStyle the style of the text for the choices
 * @param columnStyle the style of the column wrapping the choices
 * @param values the choices
 * @param labels the displayable names of the choices
 * @param selected the state managing the currently selected item
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun <T> SingleChoiceDialog(
    showDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { showDialog(false) },
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current,
    buttonStyle: ButtonStyle = LocalButtonStyle.current,
    buttonTextStyle: WordStyle = LocalWordStyle.current,
    radioButtonStyle: RadioButtonStyle = LocalRadioButtonStyle.current,
    labelStyle: WordStyle = LocalWordStyle.current,
    columnStyle: LazyColumnStyle = LocalLazyColumnStyle.current,
    values: List<T>,
    labels: List<String>,
    selected: T?,
    onStateChanged: (T) -> Unit,
) = SingleChoiceDialog(
    setShowDialog = showDialog,
    onDismissRequest = onDismissRequest,
    title = title,
    titleStyle = titleStyle,
    buttonStyle = buttonStyle,
    buttonTextStyle = buttonTextStyle,
    selected = selected,
    onStateChanged = onStateChanged,
) {
    LazyColumn(style = LazyColumnStyle(modifier = Modifier.fillMaxWidth()).with(columnStyle)) {
        itemsIndexed(values) { index, value ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = value == selected,
                    onClick = { onStateChanged(value) },
                    style = radioButtonStyle,
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(text = labels.getOrNull(index) ?: "", style = WordStyle(textStyle = MaterialTheme.typography.body1).with(labelStyle))
            }
        }
    }
}