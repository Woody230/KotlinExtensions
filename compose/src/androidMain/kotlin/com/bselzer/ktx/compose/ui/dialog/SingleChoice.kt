package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.appbar.MaterialAppBarTitle

/**
 * Lays out a dialog for choosing a single item out of multiple items.
 *
 * @param modifier the dialog modifier
 * @param showDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param properties the properties
 * @param title the title describing the choices
 * @param titleStyle the style of the text for displaying the [title]
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonColors the colors of the dialog buttons
 * @param selected the state managing the currently selected item
 * @param onStateChanged the block for setting the updated state
 * @param choices the block for laying out the item choices
 */
@Composable
fun <T> SingleChoiceDialog(
    modifier: Modifier = Modifier,
    showDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { showDialog(false) },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    selected: MutableState<T?>,
    onStateChanged: (T) -> Unit,
    choices: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Surface(
            modifier = modifier,
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor
        ) {
            ConstraintLayout {
                val (titleBox, topDivider, choiceBox, buttons) = createRefs()

                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .defaultMinSize(minHeight = 64.dp)
                        .constrainAs(titleBox) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start, 24.dp)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                ) {
                    MaterialAppBarTitle(title = title, style = titleStyle)
                }

                Divider(thickness = 1.dp, modifier = Modifier.constrainAs(topDivider) {
                    top.linkTo(titleBox.bottom)
                    centerHorizontallyTo(parent)
                })

                Box(
                    modifier = Modifier.constrainAs(choiceBox) {
                        top.linkTo(topDivider.bottom)
                        bottom.linkTo(buttons.top)
                        start.linkTo(parent.start, 24.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    }
                ) {
                    choices()
                }

                Column(
                    modifier = Modifier.constrainAs(buttons) {
                        bottom.linkTo(parent.bottom)
                        centerHorizontallyTo(parent)
                    }
                ) {
                    Divider(thickness = 1.dp)
                    MaterialAlertDialogButtons(
                        negativeButton = {
                            DismissButton(textStyle = buttonStyle, colors = buttonColors) { showDialog(false) }
                        },
                        positiveButton = {
                            ConfirmationButton(textStyle = buttonStyle, colors = buttonColors, enabled = selected.value != null) {
                                selected.value?.let { selected ->
                                    onStateChanged(selected)
                                    showDialog(false)
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

/**
 * Lays out a dialog for choosing a single item out of multiple items.
 *
 * @param modifier the dialog modifier
 * @param showDialog the block for setting whether the dialog should be shown
 * @param onDismissRequest the block for when the user tries to dismiss the dialog by clicking outside or pressing the back button
 * @param shape the dialog shape
 * @param backgroundColor the color of the dialog background
 * @param contentColor the color of the dialog content
 * @param properties the properties
 * @param title the title describing the choices
 * @param titleStyle the style of the text for displaying the [title]
 * @param buttonStyle the style of the text for the dialog buttons
 * @param buttonColors the colors of the dialog buttons
 * @param radioButtonColors the colors of the choice buttons
 * @param labelStyle the style of the text for the choices
 * @param verticalArrangement the vertical arrangement of the choices
 * @param horizontalAlignment the horizontal alignment of the choices
 * @param flingBehavior the scrollable fling behavior of the choices
 * @param values the choices
 * @param labels the displayable names of the choices
 * @param selected the state managing the currently selected item
 * @param onStateChanged the block for setting the updated state
 */
@Composable
fun <T> SingleChoiceDialog(
    modifier: Modifier = Modifier,
    showDialog: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = { showDialog(false) },
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    properties: DialogProperties = DialogProperties(),
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.h6,
    buttonStyle: TextStyle = MaterialTheme.typography.button,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    radioButtonColors: RadioButtonColors = RadioButtonDefaults.colors(),
    labelStyle: TextStyle = MaterialTheme.typography.body1,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    flingBehavior: FlingBehavior = ScrollableDefaults.flingBehavior(),
    values: List<T>,
    labels: List<String>,
    listState: LazyListState = LazyListState(),
    selected: MutableState<T?>,
    onStateChanged: (T) -> Unit,
) = SingleChoiceDialog(
    modifier = modifier,
    showDialog = showDialog,
    onDismissRequest = onDismissRequest,
    shape = shape,
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    properties = properties,
    title = title,
    titleStyle = titleStyle,
    buttonStyle = buttonStyle,
    buttonColors = buttonColors,
    selected = selected,
    onStateChanged = onStateChanged,
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = listState,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        flingBehavior = flingBehavior,
    ) {
        itemsIndexed(values) { index, value ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = value == selected.value,
                    onClick = { selected.value = value },
                    colors = radioButtonColors,
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(text = labels.getOrNull(index) ?: "", style = labelStyle)
            }
        }
    }
}